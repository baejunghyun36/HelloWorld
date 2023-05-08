package com.project.helloworld.service;

import com.project.helloworld.domain.User;
import com.project.helloworld.dto.Response;
import com.project.helloworld.dto.VisitorResponseDto;
import com.project.helloworld.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatisticServiceImpl implements StatisticService{

    private final Response response;
    private final UserRepository userRepository;
    private final VisitorService visitorService;

    @Override
    public ResponseEntity<?> getStatisticInfo(Long userSeq) throws Exception{
        User user = userRepository.findByUserSeq(userSeq).orElseThrow(()-> new Exception("해당하는 유저가 없습니다." + userSeq));

        List<VisitorResponseDto.WeeklyTodayInfo> weeklyTodayInfoList = visitorService.getWeeklyToday(userSeq);
        for(int i=0; i< weeklyTodayInfoList.size(); i++){
            System.out.println(weeklyTodayInfoList);
        }

        return response.success("", "통계 정보 조회를 성공했습니다.", HttpStatus.OK);
    }
}
