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

    @Override
    public ResponseEntity<?> requestFamily(Long userSeq, Long toUserSeq) throws Exception {
        User user = userRepository.findById(userSeq).orElseThrow(() -> new Exception("not exist user : " + userSeq));
        Family family = Family.builder().user(user).familyUserSeq(toUserSeq).
                build();
        familyRepository.save(family);
        MessageResponse messageResponse = MessageResponse.builder().message("요청을 보냈습니다.").build();
        return ResponseEntity.ok().body(messageResponse);
    }

    @Override
    public ResponseEntity<?> acceptFamily(Long familySeq) throws Exception {
        Family family = familyRepository.findById(familySeq).orElseThrow(() -> new Exception("not exist family relation : "+familySeq));
        Family newFamily = family.builder().familySeq(family.getFamilySeq())
                .relationName(family.getRelationName()).relationComment(family.getRelationComment())
                .familyUserSeq(family.getFamilyUserSeq()).isAccepted(true).familyUserNickname(family.getFamilyUserNickname())
                .user(family.getUser())
                .build();
        familyRepository.save(newFamily);
        MessageResponse messageResponse = MessageResponse.builder().message("일촌 수락 하셨습니다.").build();

        return ResponseEntity.ok().body(messageResponse);
    }

    @Override
    public ResponseEntity<?> deleteFamily(Long familySeq) throws Exception {

        familyRepository.deleteById(familySeq);
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
