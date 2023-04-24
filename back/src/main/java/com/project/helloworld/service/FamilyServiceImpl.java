package com.project.helloworld.service;


import com.project.helloworld.domain.Family;
import com.project.helloworld.domain.User;
import com.project.helloworld.dto.FamilyResponseDto;
import com.project.helloworld.dto.GuestBookDto;
import com.project.helloworld.repository.FamilyRepository;
import com.project.helloworld.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class FamilyServiceImpl implements FamilyService {


    private final FamilyRepository familyRepository;

    private final UserRepository userRepository;
    @Override
    public ResponseEntity<?> getFamily(Long userSeq) throws Exception {
        User user = userRepository.findById(userSeq).orElseThrow(()-> new Exception("not exist user : " + userSeq));
        List<Family> family = user.getFamilies();

        List<FamilyResponseDto> familyResponseDtos = family.stream().map(x -> new FamilyResponseDto(x)).collect(Collectors.toList());

        return new ResponseEntity<>(familyResponseDtos,HttpStatus.OK);
    }
}
