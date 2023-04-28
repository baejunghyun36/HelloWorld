package com.project.StoryModule.controller;

import com.project.StoryModule.service.StoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/story")
@Slf4j
public class StoryController {
    private final StoryService storyService ;

    @GetMapping("/{storySeq}")
    ResponseEntity<?> getStory(@PathVariable Long storySeq){
        log.info("storySeq: {}", storySeq);
        return storyService.getStory(storySeq);
    }
    @GetMapping("/all/{userSeq}")
    ResponseEntity<?> getAllStory(@PathVariable Long userSeq) throws Exception{
        log.info("userSeq: {} ", userSeq);
        return storyService.getAllStory(userSeq);
    }
}
