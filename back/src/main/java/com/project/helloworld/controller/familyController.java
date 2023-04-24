package com.project.helloworld.controller;

import com.project.helloworld.dto.request.FamilyCommentBody;
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

    // 일촌 조회
    @GetMapping("/{userSeq}")
      ResponseEntity<?> getFamily(@PathVariable("userSeq") Long userSeq) throws Exception {

        return ResponseEntity.ok().body(familyService.getFamily(userSeq));
    }

    // 일촌요청
    @PostMapping("")
    ResponseEntity<?> requestFamily(@RequestParam("fromUserSeq") Long fromUserSeq, @RequestParam("toUserSeq") Long toUserSeq) throws Exception {
        return ResponseEntity.ok().body(familyService.requestFamily(fromUserSeq,toUserSeq));
    }

    //일촌 수락
    @PutMapping("")
    ResponseEntity<?> acceptFamily(@RequestParam("familySeq") Long familySeq) throws Exception {
        return ResponseEntity.ok().body(familyService.acceptFamily(familySeq));
    }
    // 일촌 거절
    @DeleteMapping("")
    ResponseEntity<?> deleteFamily(@RequestParam("familySeq") Long familySeq) throws Exception{
        return ResponseEntity.ok().body(familyService.deleteFamily(familySeq));
    }

    // 일촌평 작성
    // 일촌평 수정
    // 일촌평 삭제
    @PutMapping("/comment")
    ResponseEntity<?> updateFamilyComment(@RequestBody FamilyCommentBody familyCommentBody) throws Exception {
        return ResponseEntity.ok().body(familyService.updateFamilyComment(familyCommentBody));
    }

    // 일촌평 조회
    @GetMapping("/comment")
    ResponseEntity<?> getFamilyComment(@RequestParam("userSeq") Long userSeq) throws Exception{
        return ResponseEntity.ok().body(familyService.getFamilyComment(userSeq));
    }








}
