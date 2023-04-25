package com.project.helloworld.service;


import com.project.helloworld.domain.Family;
import com.project.helloworld.domain.User;
import com.project.helloworld.dto.FamilyResponseDto;
import com.project.helloworld.dto.GuestBookDto;
import com.project.helloworld.dto.MessageResponse;
import com.project.helloworld.dto.request.FamilyCommentBody;
import com.project.helloworld.repository.FamilyRepository;
import com.project.helloworld.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class FamilyServiceImpl implements FamilyService {


    private final FamilyRepository familyRepository;

    private final UserRepository userRepository;
    @Override
    public ResponseEntity<?> getFamily(Long userSeq) throws Exception {
        User user = userRepository.findById(userSeq).orElseThrow(()-> new Exception("not exist user : " + userSeq));
        List<Family> family = user.getFamilies();

        List<FamilyResponseDto> familyResponseDtos = family.stream().map(x -> new FamilyResponseDto(x)).collect(Collectors.toList());

        return new ResponseEntity<>(familyResponseDtos,HttpStatus.OK);
    }

    // 일촌 요청을 보내!!!
    // 반대도 저장
    @Override
    public ResponseEntity<?> requestFamily(Long userSeq, Long toUserSeq) throws Exception {
        User user = userRepository.findById(userSeq).orElseThrow(() -> new Exception("not exist user : " + userSeq));
        Family family = Family.builder().relationName("from-> to 일촌명").user(user).familyUserSeq(toUserSeq).
                build();
        familyRepository.save(family);
        // 반대방향도 저장
        User userReverse = userRepository.findById(toUserSeq).orElseThrow(() -> new Exception("not exist user: " + toUserSeq));
        Family familyReverse = Family.builder().relationName("to -> from 일촌명").user(userReverse).familyUserSeq(userSeq).build();
        familyRepository.save(familyReverse);
        MessageResponse messageResponse = MessageResponse.builder().message("요청을 보냈습니다.").build();
        return ResponseEntity.ok().body(messageResponse);
    }

    @Override
    public ResponseEntity<?> acceptFamily(Long familySeq) throws Exception {
        // 정방향 수락
        Family family = familyRepository.findById(familySeq).orElseThrow(() -> new Exception("not exist family relation : "+familySeq));
        Family newFamily = family.builder().familySeq(family.getFamilySeq())
                .relationName(family.getRelationName()).relationComment(family.getRelationComment())
                .familyUserSeq(family.getFamilyUserSeq()).isAccepted(true).familyUserNickname(family.getFamilyUserNickname())
                .user(family.getUser())
                .build();
        familyRepository.save(newFamily);
        // 반대 방향도 수락 해야지 familySeq 구한다음
        Long familySeqReverse = familyRepository.findByUsers(family.getFamilyUserSeq(),family.getUser().getUserSeq());
        // 그다음 진행
        Family familyReverse = familyRepository.findById(familySeqReverse).orElseThrow(() -> new Exception("not exist family relation : "+family.getFamilyUserSeq()));
        Family newFamilyReverse = family.builder().familySeq(familyReverse.getFamilySeq())
                        .relationName(familyReverse.getRelationName()).relationComment(familyReverse.getRelationComment())
                .familyUserSeq(familyReverse.getFamilyUserSeq()).relationComment(familyReverse.getRelationComment())
                .user(family.getUser())
                .build();
        MessageResponse messageResponse = MessageResponse.builder().message("일촌 수락 하셨습니다.").build();

        return ResponseEntity.ok().body(messageResponse);
    }

    @Override
    public ResponseEntity<?> deleteFamily(Long familySeq) throws Exception {
        Family family = familyRepository.findById(familySeq).orElseThrow(() ->new Exception("not exist family relation : "+familySeq));
        Long familySeqReverse = familyRepository.findByUsers(family.getFamilyUserSeq(),family.getUser().getUserSeq());
        // 정방향
        familyRepository.deleteById(familySeq);
        // 역방향
        familyRepository.deleteById(familySeqReverse);
        MessageResponse messageResponse = MessageResponse.builder().message("일촌이 끊어졌습니다.").build();
        return ResponseEntity.ok().body(messageResponse);
    }

    @Override
    public ResponseEntity<?> getFamilyComment(Long userSeq) throws Exception {

        User user = userRepository.findById(userSeq).orElseThrow(()-> new Exception("not exist user : " + userSeq));
        List<Family> families = user.getFamilies().stream().filter(data -> data.getRelationComment() != null).collect(Collectors.toList());
        return ResponseEntity.ok().body(families);
    }

    @Override
    public ResponseEntity<?> updateFamilyComment(FamilyCommentBody familyCommentBody) throws Exception {
        Family family = familyRepository.findById(familyCommentBody.getFamilySeq()).orElseThrow(() -> new Exception("not exist familySeq : "+familyCommentBody.getFamilySeq()));
        Family newFamily = family.builder().familySeq(family.getFamilySeq())
                .relationName(family.getRelationName()).relationComment(familyCommentBody.getComment())
                .familyUserSeq(family.getFamilyUserSeq()).isAccepted(family.isAccepted()).familyUserNickname(family.getFamilyUserNickname())
                .user(family.getUser())
                .build();
        familyRepository.save(newFamily);
        return ResponseEntity.ok().body(newFamily);
    }


}
