package com.project.helloworld.interceptor;

import com.project.helloworld.security.jwt.JwtAuthenticationFilter;
import com.project.helloworld.security.jwt.JwtTokenProvider;
import com.project.helloworld.service.VisitorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class VisitorInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final VisitorService visitorService;
    private String ownerPrefix = "owner:";
    private String  todayVisitor = " todayVisitor";
    private String  totalVisitor = " totalVisitor";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 존재하지 않는 userSeq에 대해서도 redis에 저장됨;
        String path = request.getRequestURI();
        String[] pathSplit = path.split("/");
        String ownerSeq = pathSplit[3]; // 홈페이지 주인 seq

        String token = jwtAuthenticationFilter.parseBearerToken(request);
        Long visitorSeq = jwtTokenProvider.getUserSeq(token); // 방문자 seq
        String visitorId = jwtTokenProvider.getUserEmail(token);

        if(ownerSeq == null){
            System.out.println("--------------ownerSeq 추출 못햇다--------------------------");
            return true;
        }
        if(Long.valueOf(ownerSeq) == visitorSeq){ // 자기 홈페이지 조회시엔 카운팅 안되게끔
            return true;
        }

        // HyperLogLog을 사용하여 중복 방문자를 제거한 총 방문자 수 및 하루 방문자 수 갱신
        visitorService.addVisitor(ownerSeq, visitorId);

        System.out.println("조회한 사람 seq " + visitorSeq);
        System.out.println("홈페이지 주인 seq " + ownerSeq);

        System.out.println("----------------------------------------Interceptor-----------------------------------");

        System.out.println("-----------------------------------------today, total---------------------------------");
        System.out.println("총 방문자 수 " +         visitorService.getTotalVisitors(ownerSeq));
        System.out.println("오늘 방문자 수 " +         visitorService.getTodayVisitors(ownerSeq));


//        redisTemplate.delete(todayVisitorKey);
//        redisTemplate.delete(totalVisitorKey);

        // Request에 방문자 수 정보를 추가하여 Controller에서 사용
//        request.setAttribute("todayVisitorCount", todayVisitorCount);
//        request.setAttribute("totalVisitorCount", totalVisitorCount);

        return true;
    }
}
