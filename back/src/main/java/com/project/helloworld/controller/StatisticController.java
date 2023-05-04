package com.project.helloworld.controller;

import com.project.helloworld.service.VisitorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/statistic")
@Api(tags = "통계 API")
public class StatisticController {

    private final VisitorService visitorService;

    @ApiOperation(value = "7일간의 Today 정보 조회", notes = "userSeq를 인자로 받음")
    @GetMapping("/{userSeq}")
    public ResponseEntity<?> getWeeklyToday(@RequestParam Long userSeq) throws Exception {
        log.debug("userSeq ", userSeq);

        return visitorService.getWeeklyToday(userSeq);
    }
}
