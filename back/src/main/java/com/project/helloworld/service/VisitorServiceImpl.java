package com.project.helloworld.service;

import com.project.helloworld.domain.User;
import com.project.helloworld.dto.Response;
import com.project.helloworld.repository.TodayVisitRepository;
import com.project.helloworld.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HyperLogLogOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class VisitorServiceImpl implements VisitorService {

    private String ownerPrefix = "owner:";
    private String todayVisitor = " todayVisitor";
    private String totalVisitor = " totalVisitor";
    private final Response response;
    private final UserRepository userRepository;
    private final TodayVisitRepository todayVisitRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    public void addVisitor(String ownerSeq, String visitorId) {
        String totalVisitorKey = ownerPrefix + ownerSeq + totalVisitor;
        String todayVisitorKey = ownerPrefix + ownerSeq + todayVisitor;

        HyperLogLogOperations<String, Object> ops = redisTemplate.opsForHyperLogLog();
        LocalDate todayDate = LocalDate.now();
        ops.add(todayVisitorKey, visitorId);
        ops.add(totalVisitorKey, visitorId + todayDate);

        Duration expireTime = Duration.ofDays(1);

        // 현재시간으로부터 하루 뒤 자동 만료
        redisTemplate.expire(todayVisitorKey, expireTime.getSeconds(), TimeUnit.SECONDS);
    }

    public Long getTotalVisitors(String ownerSeq) {
        HyperLogLogOperations<String, Object> ops = redisTemplate.opsForHyperLogLog();
        String totalVisitorKey = ownerPrefix + ownerSeq + totalVisitor;
        Long totalVisitor = ops.size(totalVisitorKey);

        return totalVisitor;
    }

    public Long getTodayVisitors(String ownerSeq) {
        HyperLogLogOperations<String, Object> ops = redisTemplate.opsForHyperLogLog();
        String todayVisitorKey = ownerPrefix + ownerSeq + todayVisitor;
        Long todayVisitors = ops.size(todayVisitorKey);

        return todayVisitors;
    }

    // today key 만료시키기
    public void expireTodayVisitors(String ownerSeq) {
        String todayVisitorKey = ownerPrefix + ownerSeq + todayVisitor;
        redisTemplate.expire(todayVisitorKey, 0, TimeUnit.SECONDS);
    }

    // 최근 7일간 Today 정보 조회
    @Override
    public ResponseEntity<?> getWeeklyToday(Long userSeq) throws Exception {
        User user = userRepository.findByUserSeq(userSeq).orElseThrow(()-> new Exception("해당하는 유저가 없습니다." + userSeq));
        LocalDate today = LocalDate.now();
        LocalDate weekAgo = today.minusDays(7);

        // 현재부터 7일전 Today 정보 조회
        List<Object[]> todayVisitList = todayVisitRepository.findByTodayCntListByUserSeq(userSeq, weekAgo, today);

        return response.success(todayVisitList, "weekly Today 정보 조회에 성공했습니다.", HttpStatus.OK);
    }
}
