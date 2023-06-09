package com.project.helloworld.service;

import com.project.helloworld.domain.*;
import com.project.helloworld.dto.*;
import com.project.helloworld.dto.response.BgmList;
import com.project.helloworld.repository.*;
import com.project.helloworld.security.jwt.JwtTokenProvider;
import com.project.helloworld.security.oauth2.AuthProvider;
import com.project.helloworld.util.Authority;
import com.project.helloworld.security.SecurityUtil;
import com.project.helloworld.util.S3Uploader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService{

    @Value("${spring.mail.username}")
    private String from; // 발신자 메일(관리자)

    // SMS api key
    @Value("${spring.sms.key}")
    private String smsKey;
    @Value("${spring.sms.secret-key}")
    private String secretKey;
    @Value("${spring.sms.sender}")
    private String sender;

    private final BgmRepository bgmRepository;
    private final UserRepository userRepository;
    private final Response response;
    private final PasswordEncoder encoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate redisTemplate;
    private final JavaMailSender javaMailSender;
    private final CertificationDto certificationDto;
    private final PasswordEncoder passwordEncoder;
    private final FamilyService familyService;
    private final GrassService grassService;
    private final VisitorService visitorService;
    private final S3Uploader s3Uploader;
    private final EntityManager em;
    DefaultMessageService messageService;
    private final FamilyRepository familyRepository;
    private final StickerRepository stickerRepository;
    private String ownerPrefix = "owner:";
    private String todayVisitor = " todayVisitor";
    private String totalVisitor = " totalVisitor";

    @Override
    public ResponseEntity<?> signUp(UserRequestDto.SignUp signUp, MultipartFile img) throws IOException {
        if(userRepository.existsByEmail(signUp.getEmail())){
            return response.fail("이미 회원가입된 유저입니다.", HttpStatus.BAD_REQUEST);
        }

        String avatarUrl = null;
        Avatar avatar = new Avatar();
        if(img.isEmpty()) return response.fail("아바타가 비었습니다.", HttpStatus.BAD_REQUEST);
        try{
            avatarUrl = s3Uploader.uploadFiles(img, "avatar");
            avatar.setImgUrl(avatarUrl);
            em.persist(avatar);
        }catch (Exception e){
            return response.fail("아바타 이미지 업로드 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        User user = User.builder()
                .nickname(signUp.getNickname())
                .email(signUp.getEmail())
                .password(encoder.encode(signUp.getPassword()))
                .name(signUp.getName())
                .phoneNumber(signUp.getPhoneNumber())
                .avatar(avatar)
                .roles(Collections.singletonList(Authority.ROLE_USER.name()))
                .authProvider(AuthProvider.local)
                .build();
        userRepository.save(user);

        return response.success("회원가입에 성공했습니다.");
    }

    @Override
    public ResponseEntity<?> signIn(UserRequestDto.SignIn signIn) throws Exception{
        User user = userRepository.findByEmail(signIn.getEmail()).orElseThrow(()-> new Exception("해당하는 유저가 없습니다." + signIn.getEmail()));
        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = signIn.toAuthentication();

        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        UserResponseDto.TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);
        tokenInfo.setUserSeq(user.getUserSeq());

        // 4. RefreshToken Redis 저장 (expirationTime 설정을 통해 자동 삭제 처리)
        redisTemplate.opsForValue()
                .set("RT:" + authentication.getName(), tokenInfo.getRefreshToken(), tokenInfo.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);

        return response.success(tokenInfo, "로그인에 성공했습니다.", HttpStatus.OK);
    }

    // 토큰 재발급
    @Override
    public ResponseEntity<?> reissue(UserRequestDto.Reissue reissue) {
        // 1. Refresh Token 검증
        if (!jwtTokenProvider.validateToken(reissue.getRefreshToken())) {
            return response.fail("Refresh Token 정보가 유효하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        // 2. Access Token 에서 User email 를 가져옵니다.
        Authentication authentication = jwtTokenProvider.getAuthentication(reissue.getAccessToken());

        // 3. Redis 에서 User email 을 기반으로 저장된 Refresh Token 값을 가져옵니다.
        String refreshToken = (String)redisTemplate.opsForValue().get("RT:" + authentication.getName());
        if(!refreshToken.equals(reissue.getRefreshToken())) {
            return response.fail("Refresh Token 정보가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        // 4. 새로운 토큰 생성
        UserResponseDto.TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        // 5. RefreshToken Redis 업데이트
        redisTemplate.opsForValue()
                .set("RT:" + authentication.getName(), tokenInfo.getRefreshToken(), tokenInfo.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);

        return response.success(tokenInfo, "Token 정보가 갱신되었습니다.", HttpStatus.OK);
    }

    // 회원정보 조회
    @Override
    public ResponseEntity<?> getUserInfo(Long userSeq) throws Exception{
        User user = userRepository.findByUserSeq(userSeq).orElseThrow(()-> new Exception("해당하는 유저가 없습니다." + userSeq));

        Long today = visitorService.getTodayVisitors(String.valueOf(userSeq));
        Long total = visitorService.getTotalVisitors(String.valueOf(userSeq));
        UserResponseDto.UserInfo userInfo = UserResponseDto.UserInfo.builder()
                .userSeq(userSeq)
                .email(user.getEmail())
                .nickname(user.getNickname())
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .comment(user.getComment())
                .today(today)
                .total(total)
                .likeCnt(user.getLikeCnt())
                .helpfulCnt(user.getHelpfulCnt())
                .understandCnt(user.getUnderstandCnt())
                .backgroundUrl(user.getBackgroundUrl())
                .avatarUrl(user.getAvatar().getImgUrl())
                .providerId(user.getProviderId())
                .authProvider(user.getAuthProvider())
                .build();

        List<Bgm> bgms = bgmRepository.findAll();
        Collections.shuffle(bgms);

        List<BgmList> bgmList = bgms.stream().map(bgm -> {
            BgmList list = new BgmList();
            list.setBgmSeq(bgm.getBgmSeq());
            list.setVideoId(bgm.getVideoId());
            list.setTitle(bgm.getTitle());
            list.setArtist(bgm.getArtist());
            return list;
        }).collect(Collectors.toList());

        userInfo.setBgmList(bgmList);

        String userEmail = SecurityUtil.getCurrentUserEmail();
        user = userRepository.findByEmail(userEmail).orElseThrow(()-> new Exception("해당하는 유저가 없습니다." + userSeq));
        Long visitorSeq = user.getUserSeq();
        Long masterSeq = userSeq;
        userInfo.setIsFamily(familyService.getFamilyByUser(masterSeq, visitorSeq));

        return response.success(userInfo, "유저 정보가 조회되었습니다.", HttpStatus.OK);
    }

    // 메인페이지 접속했을때 돌려줄 정보
    public ResponseEntity<?> getUserMainInfo(Long userSeq) throws Exception {
        User user = userRepository.findByUserSeq(userSeq).orElseThrow(()-> new Exception("해당하는 유저가 없습니다." + userSeq));

        Long todayCnt = visitorService.getTodayVisitors(String.valueOf(userSeq));
        Long totalCnt = visitorService.getTotalVisitors(String.valueOf(userSeq));

        SecurityContextHolder.getContext().getAuthentication();
        UserResponseDto.UserMainInfo userMainInfo = UserResponseDto.UserMainInfo.builder()
                .userSeq(user.getUserSeq())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .name(user.getName())
                .comment(user.getComment())
                .phoneNumber(user.getPhoneNumber())
                .likeCnt(user.getLikeCnt())
                .helpfulCnt(user.getHelpfulCnt())
                .understandCnt(user.getUnderstandCnt())
                .today(todayCnt)
                .total(totalCnt)
                .backgroundUrl(user.getBackgroundUrl())
                .avatarUrl(user.getAvatar().getImgUrl())
                .build();


        List<Bgm> bgms = bgmRepository.findAll();
        Collections.shuffle(bgms);

        List<BgmList> bgmList = bgms.stream().map(bgm -> {
            BgmList list = new BgmList();
            list.setBgmSeq(bgm.getBgmSeq());
            list.setVideoId(bgm.getVideoId());
            list.setTitle(bgm.getTitle());
            list.setArtist(bgm.getArtist());
            return list;
        }).collect(Collectors.toList());

        userMainInfo.setBgmList(bgmList);
        LocalDate today = LocalDate.now();
        LocalDate oneYearAgo = today.minusYears(1);

        // 잔디 정보 매핑
        ResponseEntity<?> grassInfoList = grassService.getGrass(oneYearAgo, today, userSeq);
        userMainInfo.getGrassList(grassInfoList.getBody());

        /**
         * 일촌 정보 매핑
         * 0 : 관계 없는 사람
         * 1 : 내가 신청 받은 상태
         * 2 : 내가 신청 한 상태
         * 3 : 이미 일촌인 상태
         */
        ResponseEntity familiesCommentInfo = familyService.getFamilies(userSeq, "accepted", true);
        userMainInfo.getFamilyResponseDtos(familiesCommentInfo.getBody());

        String userEmail = SecurityUtil.getCurrentUserEmail();
        user = userRepository.findByEmail(userEmail).orElseThrow(()-> new Exception("해당하는 유저가 없습니다." + userSeq));
        Long visitorSeq = user.getUserSeq();
        Long masterSeq = userSeq;
        userMainInfo.setIsFamily(familyService.getFamilyByUser(masterSeq, visitorSeq));

        return response.success(userMainInfo, "유저 메인페이지 정보 조회가 성공했습니다.", HttpStatus.OK);
    }

    // 회원정보 수정
    @Override
    public ResponseEntity<?> modify(UserRequestDto.Modify modify) throws Exception{
        User user = userRepository.findById(modify.getUserSeq()).orElseThrow(()-> new Exception("해당하는 유저가 없습니다." + modify.getUserSeq()));

        user.setName(modify.getName());
        user.setNickname(modify.getNickname());
        user.setPhoneNumber(modify.getPhoneNumber());
        user.setComment(modify.getComment());

        userRepository.save(user);
        return response.success(user, "유저 정보가 수정되었습니다.", HttpStatus.OK);
    }

    // 아바타 이미지 수정
    @Override
    public ResponseEntity<?> modifyAvatar(Long userSeq, MultipartFile img) throws Exception {
        User user = userRepository.findByUserSeq(userSeq).orElseThrow(()-> new Exception("해당하는 유저가 없습니다." + userSeq));
        
        String avatarUrl = null;
        Avatar avatar = new Avatar();
        if(img.isEmpty()) return response.fail("아바타가 비었습니다.", HttpStatus.BAD_REQUEST);
        try{
            avatarUrl = s3Uploader.uploadFiles(img, "avatar");
            avatar.setImgUrl(avatarUrl);
            em.persist(avatar);
            user.setAvatar(avatar);
        }catch (Exception e){
            return response.fail("아바타 이미지 수정 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response.success(avatarUrl, "아바타 이미지 수정 성공", HttpStatus.OK);
    }

    // 비밀번호 변경
    @Override
    public ResponseEntity<?> changePassword(UserRequestDto.ChangePassword changePassword) throws Exception {
        User user = userRepository.findByUserSeq(changePassword.getUserSeq()).orElseThrow(()-> new Exception("해당하는 유저가 없습니다" + changePassword.getUserSeq()));
        if(passwordEncoder.matches(changePassword.getOriginPassword(), user.getPassword())){ // 비밀번호가 일치하면
            // 입력받은 비밀번호로 변경
            user.setPassword(passwordEncoder.encode(changePassword.getChangePassword()));
            userRepository.save(user);
        }

        return response.success(user, "비밀번호가 변경됐습니다.", HttpStatus.OK);
    }

    // 회원 탈퇴
    @Override
    public ResponseEntity<?> delete(Long userSeq) throws Exception{
        User user = userRepository.findByUserSeq(userSeq).orElseThrow(()-> new Exception("해당하는 유저가 없습니다." + userSeq));

        familyRepository.deleteByUserSeq(userSeq);
        userRepository.deleteByUserSeq(userSeq);
        // 회원탈퇴와 함께 redis에 저장된 RT, Today, Total 모두 만료
        redisTemplate.expire("RT:" + user.getEmail(), 0, TimeUnit.SECONDS);
        String todayVisitorKey = ownerPrefix + userSeq + todayVisitor;
        String totalVisitorKey = ownerPrefix + userSeq + totalVisitor;
        redisTemplate.expire(todayVisitorKey, 0, TimeUnit.SECONDS);
        redisTemplate.expire(totalVisitorKey, 0, TimeUnit.SECONDS);

        return response.success("", "회원 탈퇴 성공했습니다.", HttpStatus.OK);
    }

    public ResponseEntity<?> authority() {
        // SecurityContext에 담겨 있는 authentication userEamil 정보
        String userEmail = SecurityUtil.getCurrentUserEmail();

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("No authentication information."));

        // add ROLE_ADMIN
        user.getRoles().add(Authority.ROLE_ADMIN.name());
        userRepository.save(user);

        return response.success();
    }

    // 회원 로그아웃 - 토큰 만료
    @Override
    public ResponseEntity<?> logout(UserRequestDto.Logout logout) throws Exception {
        // 1. Access Token 검증
        if (!jwtTokenProvider.validateToken(logout.getAccessToken())) {
            return response.fail("잘못된 요청입니다.", HttpStatus.BAD_REQUEST);
        }

        // 2. Access Token 에서 User email 을 가져옵니다.
        Authentication authentication = jwtTokenProvider.getAuthentication(logout.getAccessToken());

        // 3. Redis 에서 해당 User email 로 저장된 Refresh Token 이 있는지 여부를 확인 후 있을 경우 삭제합니다.
        if (redisTemplate.opsForValue().get("RT:" + authentication.getName()) != null) {
            // Refresh Token 삭제
            redisTemplate.delete("RT:" + authentication.getName());
        }

        // 4. 해당 Access Token 유효시간 가지고 와서 BlackList 로 저장하기
        Long expiration = jwtTokenProvider.getExpiration(logout.getAccessToken());
        redisTemplate.opsForValue()
                .set(logout.getAccessToken(), "logout", expiration, TimeUnit.MILLISECONDS);

        return response.success("", "로그아웃이 성공했습니다.", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getUserSearchByKeyword(UserRequestDto.UserSearchKeyword userSearchKeyword) throws Exception {
        int type = userSearchKeyword.getType();
        List<User> userList = new ArrayList<>();
        String message = null;
        switch (type){
            case 1: // 이름 
                userList = userRepository.findAllByName(userSearchKeyword.getKeyword());
                message = "사용자 이름과 관련된 리스트 조회 성공";
                break;
            case 2: // 닉네임
                userList = userRepository.findAllByNickname(userSearchKeyword.getKeyword());
                message = "사용자 닉네임과 관련된 리스트 조회 성공";
                break;
            case 3: // 이메일
                userList = userRepository.findAllByEmail(userSearchKeyword.getKeyword());
                message = "사용자 이메일과 관련된 리스트 조회 성공";
        }

        if(userList.size() == 0){
            return response.fail("Keyword에 해당하는 유저가 없습니다.",HttpStatus.NO_CONTENT);
        }

        UserResponseDto.UserSearchList userSearchList = new UserResponseDto.UserSearchList();
        List<UserResponseDto.UserSearchList.SearchInfo> userSearchLists = new ArrayList<>();
        for (User user : userList) {
            int isFamily = familyService.getFamilyByUser(userSearchKeyword.getMasterSeq(), user.getUserSeq());

            UserResponseDto.UserSearchList.SearchInfo searchInfo = UserResponseDto.UserSearchList.SearchInfo.builder()
                    .userSeq(user.getUserSeq())
                    .name(user.getName())
                    .nickname(user.getNickname())
                    .isFamily(isFamily)
                    .avatarUrl(user.getAvatar().getImgUrl())
                    .build();

            userSearchLists.add(searchInfo);
        }


        return response.success(userSearchLists, message, HttpStatus.OK);
    }


    /**
     *  본인 인증 메서드
     */
    // SMS 문자 발송, 인증 문자 redis에 저장
    @Override
    public ResponseEntity<?> certifiedPhoneNumber(String phoneNumber) throws Exception {
        User user = userRepository.findByPhoneNumber(phoneNumber).orElseThrow(()-> new Exception("해당하는 유저가 없습니다." + phoneNumber));

        this.messageService = NurigoApp.INSTANCE.initialize(smsKey, secretKey, "https://api.coolsms.co.kr");
        // 유저에게 발송 후 redis에 인증 문자 저장
        sendOne(phoneNumber);

        return response.success(user, "인증 문자를 발송했습니다.", HttpStatus.OK);
    }

    // 사용자가 입력한 인증번호가 redis에 저장된 번호와 일치하는지 확인
    @Override
    public ResponseEntity<?> confirmMessage(UserRequestDto.SmsCertify smsCertify) throws Exception {
        if (isVerifySms(smsCertify)) {
            return response.fail("인증번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        // redis에서 인증번호 삭제
        certificationDto.removeCertification(smsCertify.getPhoneNumber());
        User user = userRepository.findByPhoneNumber(smsCertify.getPhoneNumber()).orElseThrow(()-> new Exception("해당하는 유저가 없습니다." + smsCertify.getPhoneNumber()));
        return response.success(user.getEmail(), "인증번호가 일치합니다.", HttpStatus.OK);
    }

    // redis에 저장된 인증번호와 유저가 입력한 인증번호 검사
    public boolean isVerifySms(UserRequestDto.SmsCertify smsCertify){
        return !(certificationDto.hasKey(smsCertify.getPhoneNumber()) &&
                certificationDto.getCertification(smsCertify.getPhoneNumber())
                        .equals(smsCertify.getMessage()));
    }

    public SingleMessageSentResponse sendOne(String phoneNumber) {
        Message message = new Message();
        message.setFrom(sender);
        message.setTo(phoneNumber);
        String smsMessage = getTempMessage();
        message.setText("인증 문자를 브라우저에서 입력하세요. " + smsMessage);
        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
        
        // redist에 인증 문자 저장
        certificationDto.createCertification(phoneNumber, smsMessage);
        return response;
    }

    // 인증 이메일 발송
    @Override
    public ResponseEntity<?> certifiedEmail(String email) throws Exception{
        userRepository.findByEmail(email).orElseThrow(()-> new Exception("해당하는 유저가 없습니다." + email));
        MailDto mail = createMailForId(email);
        mailSend(mail, from);

        return response.success("", "인증 메세지를 발송했습니다.", HttpStatus.OK);
    }

    // 이메일 인증 메세지 검증
    @Override
    public ResponseEntity<?> confirmEmail(UserRequestDto.EmailCertify emailCertify) throws Exception {
        if (isVerifyEmail(emailCertify)) {
            return response.fail("인증번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        User user = userRepository.findByEmail(emailCertify.getEmail()).orElseThrow(()-> new Exception("해당하는 유저가 없습니다." + emailCertify.getEmail()));
        // redis에서 인증번호 삭제
        certificationDto.removeCertification(emailCertify.getEmail());
        
        // 임시 비밀번호 발급하고 DB 갱신
        createMailAndChangePassword(user, emailCertify.getEmail());

        return response.success("", "인증 메세지가 일치합니다.", HttpStatus.OK);
    }

    public boolean isVerifyEmail(UserRequestDto.EmailCertify emailCertify){
        return !(certificationDto.hasKey(emailCertify.getEmail()) &&
                certificationDto.getCertification(emailCertify.getEmail())
                        .equals(emailCertify.getMessage()));
    }

    public MailDto createMailForId(String email){
        String message = getTempMessage();

        MailDto mail = new MailDto();
        mail.setAddress(email);
        mail.setTitle("HelloWorld 이메일 인증 메세지입니다.");
        mail.setMessage("안녕하세요. HelloWorld 비밀번호 찾기 안내 관련 이메일 입니다." + " 아래의 인증 메세지를 입력해주세요 "
                + message);

        // redis에 인증 메세지 저장
        certificationDto.createCertification(email, message);
        return mail;
    }

    public void mailSend(MailDto mailDto, String from){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailDto.getAddress());
        message.setSubject(mailDto.getTitle());
        message.setText(mailDto.getMessage());
        message.setFrom(from); // 보내는 사람(관리자)
        message.setReplyTo(from);

        javaMailSender.send(message);
    }

    // 임시 비밀번호 발급
    public MailDto createMailAndChangePassword(User user, String email){
        String message = getTempMessage();
        MailDto mail = new MailDto();
        mail.setAddress(email);
        mail.setTitle("HelloWorld 임시비밀번호 안내 이메일 입니다.");
        mail.setMessage("안녕하세요. HelloWorld 임시비밀번호 안내 관련 이메일 입니다." + " 회원님의 임시 비밀번호는 "
                + message + " 입니다." + "로그인 후에 비밀번호를 변경을 해주세요");

        mailSend(mail, from);
        updatePassword(user, message);

        return mail;
    }

    // 임시 비밀번호로 업데이트
    public void updatePassword(User user, String message){
        user.setPassword(passwordEncoder.encode(message));
        userRepository.save(user);
    }

    // 인증 메세지 10자리
    public String getTempMessage(){
        char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

        String str = "";

        // 문자 배열 길이의 값을 랜덤으로 10개를 뽑아 구문을 작성
        int idx = 0;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            str += charSet[idx];
        }
        return str;
    }
}
