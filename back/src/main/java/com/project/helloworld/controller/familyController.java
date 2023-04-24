package com.project.helloworld.controller;

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
    ResponseEntity<?> requestFamily(){
        return null;
    }

    //일촌 수락

    // 일촌 거절





}
