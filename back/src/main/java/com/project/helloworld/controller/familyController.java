package com.project.helloworld.controller;

import com.project.helloworld.dto.request.FamilyCommentBody;
import com.project.helloworld.security.jwt.JwtTokenProvider;
import com.project.helloworld.service.FamilyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/family")
@RequiredArgsConstructor
public class familyController {

    private final FamilyService familyService;

    private final JwtTokenProvider jwtTokenProvider;

    // 일촌 조회
    @GetMapping("")
      ResponseEntity<?> getFamily(@RequestHeader("Authorization") String token) throws Exception {
//        Long userSeq = jwtTokenProvider.getUserSeq(token);
        Long userSeq = 1L;
        return ResponseEntity.ok().body(familyService.getFamily(userSeq));
    }

    // 일촌요청
    @PostMapping("")
    ResponseEntity<?> requestFamily(@RequestHeader("Authorization") String token, @RequestParam("toUserSeq") Long toUserSeq) throws Exception {

        // Long fromUserSeq = jwtTokenProvider.getUserSeq(token);
        Long fromUserSeq = 1L;
        return ResponseEntity.ok().body(familyService.requestFamily(fromUserSeq,toUserSeq));
    }

    //일촌 수락
    @PutMapping("")
    ResponseEntity<?> acceptFamily(@RequestHeader("Authorization") String token, @RequestParam("familySeq") Long familySeq) throws Exception {
        return ResponseEntity.ok().body(familyService.acceptFamily(familySeq));
    }
    // 일촌 거절
    @DeleteMapping("")
    ResponseEntity<?> deleteFamily(@RequestHeader("Authorization") String token, @RequestParam("familySeq") Long familySeq) throws Exception{
        return ResponseEntity.ok().body(familyService.deleteFamily(familySeq));
    }

    // 일촌평 작성
    // 일촌평 수정
    // 일촌평 삭제
    @PutMapping("/comment")
    ResponseEntity<?> updateFamilyComment(@RequestHeader("Authorization") String token , @RequestBody FamilyCommentBody familyCommentBody) throws Exception {
        return ResponseEntity.ok().body(familyService.updateFamilyComment(familyCommentBody));
    }

    // 일촌평 조회
    @GetMapping("/comment")
    ResponseEntity<?> getFamilyComment(@RequestHeader("Authorization") String token) throws Exception{

        //        Long userSeq = jwtTokenProvider.getUserSeq(token);
        Long userSeq = 1L;
        return ResponseEntity.ok().body(familyService.getFamilyComment(userSeq));
    }








}
