package com.project.helloworld.controller;

import com.project.helloworld.dto.request.FamilyCommentBody;
import com.project.helloworld.dto.request.FamilyNameBody;
import com.project.helloworld.dto.request.FamilyRequestBody;
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
    // 하나의 family 조회
    @GetMapping("/one")
    ResponseEntity<?> getFamily(@RequestParam("fromUserSeq") Long fromUserSeq,@RequestParam("toUserSeq") Long toUserSeq) throws Exception {

        return ResponseEntity.ok().body(familyService.getFamily(fromUserSeq,toUserSeq));
    }
    // 일촌 조회
    @GetMapping("")
      ResponseEntity<?> getFamilies(@RequestParam("userSeq") Long userSeq,@RequestParam("status") String status,@RequestParam("hasComment") Boolean hasComment) throws Exception {
//        @RequestHeader("Authorization") String token
//        Long userSeq = jwtTokenProvider.getUserSeq(token);

        return ResponseEntity.ok().body(familyService.getFamilies(userSeq,status,hasComment));
    }




    @PostMapping("")
    ResponseEntity<?> requestFamily(@RequestBody FamilyRequestBody familyRequestBody) throws Exception {
//        @RequestHeader("Authorization") String token
//         Long fromUserSeq = jwtTokenProvider.getUserSeq(token);

        return ResponseEntity.ok().body(familyService.requestFamily(familyRequestBody.getFromUserSeq(),familyRequestBody.getToUserSeq(),familyRequestBody.getFromRelationName(),familyRequestBody.getToRelationName(),familyRequestBody.getRequestMessage()));
    }

    //일촌 수락
    @PutMapping("")
    ResponseEntity<?> acceptFamily( @RequestParam("fromUserSeq") Long fromUserSeq, @RequestParam("toUserSeq") Long toUserSeq) throws Exception {

//        @RequestHeader("Authorization") String token,
        return ResponseEntity.ok().body(familyService.acceptFamily(fromUserSeq,toUserSeq));
    }
    // 일촌 거절
    @DeleteMapping("")
    ResponseEntity<?> deleteFamily( @RequestParam("fromUserSeq") Long fromUserSeq, @RequestParam("toUserSeq") Long toUserSeq) throws Exception{
//        @RequestHeader("Authorization") String token,
        return ResponseEntity.ok().body(familyService.deleteFamily(fromUserSeq,toUserSeq));
    }

    // 일촌평 작성
    // 일촌평 수정
    // 일촌평 삭제
    @PutMapping("/comment")
    ResponseEntity<?> updateFamilyRelationComment( @RequestBody FamilyCommentBody familyCommentBody) throws Exception {
//        @RequestHeader("Authorization") String token ,
        return ResponseEntity.ok().body(familyService.updateFamilyRelationComment(familyCommentBody));
    }

    @PutMapping("/name")
    ResponseEntity<?> updateFamilyRelationName(@RequestBody FamilyNameBody familyNameBody) throws Exception {
        return ResponseEntity.ok().body(familyService.updateFamilyRelationName(familyNameBody));
    }

    // 일촌평 조회 (얘는 사실 필요 없을 듯?)
//    @GetMapping("/comment")
//    ResponseEntity<?> getFamilyComment(@RequestParam("userSeq") Long userSeq) throws Exception{
//
////        @RequestHeader("Authorization") String token
//        //        Long userSeq = jwtTokenProvider.getUserSeq(token);
//
//        return ResponseEntity.ok().body(familyService.getFamilyComment(userSeq));
//    }



    // 랜덤 파도타기 ㅋㅋㅋ
    @GetMapping("/random")
    ResponseEntity<?> randomWind(@RequestParam Long userSeq) throws Exception {
        return ResponseEntity.ok().body(familyService.randomWind(userSeq));
    }

    // 일촌들 조회(그냥 친구목록 볼때, 순서 까지(today 대신 시간순으로 하자),검색 기능까지 추가  )
    @GetMapping("/family-wind")
    ResponseEntity<?> getFamiliesWind(@RequestParam Long userSeq, @RequestParam(required = false) String keyword) throws Exception{
        return ResponseEntity.ok().body(familyService.getFamiliesWind(userSeq,keyword));
    }

    // 친구 추천 (친구의 친구?)
    @GetMapping("/recommend")
    ResponseEntity<?> recommendFriend(@RequestParam Long userSeq) throws Exception {
        return ResponseEntity.ok().body(familyService.recommendFamily(userSeq));
    }






}
