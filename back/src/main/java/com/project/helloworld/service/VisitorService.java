package com.project.helloworld.service;

import org.springframework.http.ResponseEntity;

public interface VisitorService {
    void addVisitor(String email, String visitorId);
    Long getTotalVisitors(String ownerSeq);
    Long getTodayVisitors(String ownerSeq);
    void expireTodayVisitors(String ownerSeq);
    ResponseEntity<?> getWeeklyToday(Long userSeq) throws Exception;
}
