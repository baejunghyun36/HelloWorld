package com.project.helloworld.dto;

import com.project.helloworld.domain.Family;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class FamilyResponseDto {

    private Long userSeq;


    private String nickname;



    public FamilyResponseDto(Family family) {
        this.userSeq = family.getFamilyUserSeq();
        System.out.println("아아아"+family.getFamilyUserNickname());
        this.nickname = family.getFamilyUserNickname();

    }
}
