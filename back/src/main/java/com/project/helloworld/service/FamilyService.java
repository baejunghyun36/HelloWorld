package com.project.helloworld.service;

import com.project.helloworld.dto.FamilyResponseDto;
import com.project.helloworld.dto.request.FamilyCommentBody;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FamilyService {

    public ResponseEntity<?> getFamily(Long userSeq) throws Exception;

    public ResponseEntity<?> requestFamily(Long userSeq, Long toUserSeq ) throws Exception;

    public ResponseEntity<?> acceptFamily(Long familySeq) throws Exception;
    public ResponseEntity<?> deleteFamily(Long familySeq) throws Exception;

    public ResponseEntity<?> getFamilyComment(Long userSeq) throws Exception;

    public ResponseEntity<?> updateFamilyComment(FamilyCommentBody familyCommentBody) throws Exception;
}
