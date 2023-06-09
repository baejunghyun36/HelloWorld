package com.project.helloworld.service;

import com.project.helloworld.dto.FamilyResponseDto;
import com.project.helloworld.dto.request.FamilyCommentBody;
import com.project.helloworld.dto.request.FamilyNameBody;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FamilyService {

    public ResponseEntity<?> getFamilies(Long userSeq,String status,Boolean hasComment) throws Exception;

    public ResponseEntity<?> getFamily(Long fromUserSeq, Long toUserSeq) throws Exception;

    public Integer getFamilyByUser(Long fromUserSeq,Long toUserSeq ) throws Exception;

    public ResponseEntity<?> requestFamily(Long userSeq, Long toUserSeq,String fromRelationName,String toRelationName ,String requestMessage) throws Exception;

    public ResponseEntity<?> acceptFamily(Long fromUserSeq,Long toUserSeq) throws Exception;
    public ResponseEntity<?> deleteFamily(Long fromUserSeq, Long toUserSeq) throws Exception;



    public ResponseEntity<?> updateFamilyRelationComment(FamilyCommentBody familyCommentBody) throws Exception;

    public ResponseEntity<?> updateFamilyRelationName(FamilyNameBody familyNameBody) throws Exception;

    public ResponseEntity<?> randomWind(Long userSeq) throws Exception;

    public ResponseEntity<?> getFamiliesWind(Long userSeq,String keyword) throws Exception;

    public ResponseEntity<?> recommendFamily(Long userSeq) throws Exception;
}
