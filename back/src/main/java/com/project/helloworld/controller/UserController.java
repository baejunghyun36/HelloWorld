package com.project.helloworld.controller;

import com.project.helloworld.dto.UserRequestDto;
import com.project.helloworld.security.jwt.JwtTokenProvider;
import com.project.helloworld.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Api(tags = "User API")
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @ApiOperation(value = "회원가입", notes = "id, email, password, nickname, name")
    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@Validated @RequestBody UserRequestDto.SignUp signUp) throws Exception{
        log.debug("singUp", signUp);

        return userService.signUp(signUp);
    }

    @ApiOperation(value = "일반 로그인", notes = "id, password")
    @PostMapping("/signIn")
    public ResponseEntity<?> signIn(@Validated @RequestBody UserRequestDto.SignIn signIn) throws Exception{
        log.debug("SignIn", signIn);

        return userService.signIn(signIn);
    }

    @ApiOperation(value = "토큰 갱신", notes = "accessToken, refreshToken")
    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(@Validated @RequestBody UserRequestDto.Reissue reissue){
        log.debug("reissueInfo", reissue);

        return userService.reissue(reissue);
    }

    @ApiOperation(value = "회원정보 조회", notes = "userSeq")
    @GetMapping("/userInfo/{userSeq}")
    public ResponseEntity<?> getUserInfo(@Validated @PathVariable Long userSeq) throws Exception{
        log.debug("tokenInfo", userSeq);

        return userService.getUserInfo(userSeq);
    }

    @ApiOperation(value = "회원정보 수정", notes = "name, nickName, phoneNumber 입력받음")
    @PutMapping("/modify")
    public ResponseEntity<?> modify(@Validated @RequestBody UserRequestDto.Modify modify) throws Exception{
        log.debug("modifyInfo", modify);

        return userService.modify(modify);
    }

    @ApiOperation(value = "비밀번호 변경", notes = "userSeq, password 전달받음")
    @PutMapping("/changePassword")
    public ResponseEntity<?> changePassword(@Validated @RequestBody UserRequestDto.ChangePassword changePassword) throws Exception{
        log.debug("password", changePassword);

        return userService.changePassword(changePassword);
    }

    @ApiOperation(value = "로그아웃", notes = "accessToken, refreshToken 전달받음")
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody UserRequestDto.Logout logout) throws Exception{
        log.debug("logout", logout);

        return userService.logout(logout);
    }

    @ApiOperation(value = "회원 탈퇴", notes = "token")
    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestHeader("Authorization") String token) throws Exception{
        log.debug("token", token);
        Long userSeq = jwtTokenProvider.getUserSeq(token);

        return userService.delete(userSeq);
    }

    /**
     * 이메일 찾기
     */
    @ApiOperation(value = "SMS 인증", notes = "입력받은 전화번호로 인증용 SMS 메세지 발송")
    @PostMapping("/sms-certification/sends")
    public ResponseEntity<?> certifiedPhoneNumber(@RequestBody UserRequestDto.SmsCertify smsCertify) throws Exception{
        log.debug("phoneNumber", smsCertify.getPhoneNumber());

        return userService.certifiedPhoneNumber(smsCertify.getPhoneNumber());
    }

    @ApiOperation(value = "SMS 메세지 검증", notes = "입력받은 인증 메세지를 검증")
    @PostMapping("/sms-certification/confirms")
    public ResponseEntity<?> confirmMessage(@RequestBody UserRequestDto.SmsCertify smsCertify) throws Exception{
        log.debug("message", smsCertify);

        return userService.confirmMessage(smsCertify);
    }

    /**
     * 비밀번호 찾기
     */
    @ApiOperation(value = "이메일 인증", notes = "입력받은 이메일로 인증 메세지 발송")
    @PostMapping("/email-certification/sends")
    public ResponseEntity<?> certifiedEmail(@RequestBody UserRequestDto.EmailCertify emailCertify) throws Exception{
        log.debug("email",emailCertify.getEmail());

        return userService.certifiedEmail(emailCertify.getEmail());
    }
    
    @ApiOperation(value = "이메일 검증", notes = "입력받은 인증 메세지를 검증 후 임시 비밀번호 발송")
    @PostMapping("/email-certification/confirms")
    public ResponseEntity<?> confirmEmail(@RequestBody UserRequestDto.EmailCertify emailCertify) throws Exception{
        log.debug("message", emailCertify);

        return userService.confirmEmail(emailCertify);
    }
}