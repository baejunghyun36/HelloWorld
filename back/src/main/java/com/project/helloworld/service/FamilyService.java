package com.project.helloworld.service;

import com.project.helloworld.dto.FamilyResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FamilyService {

    public ResponseEntity<?> getFamily(Long userSeq) throws Exception;

//    public ResponseEntity<?> requestFamily( );
}
