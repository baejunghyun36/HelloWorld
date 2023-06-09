package com.project.helloworld.dto;

import com.project.helloworld.domain.User;
import com.project.helloworld.dto.response.BgmList;
import com.project.helloworld.dto.response.GrassResponse;
import com.project.helloworld.security.oauth2.AuthProvider;
import com.project.helloworld.service.FamilyService;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@RequiredArgsConstructor
public class UserResponseDto {

    @Getter
    @Setter
    @Builder
    public static class TokenInfo {
        private Long userSeq;
        private String grantType;
        private String accessToken;
        private Long accessTokenExpiresIn;
        private String refreshToken;
        private Long refreshTokenExpirationTime;
    }

    @Getter
    @Setter
    @Builder
    public static class UserInfo{
        private Long userSeq;
        private String email;
        private String name;
        private String nickname;
        private String comment;
        private String phoneNumber;
        private String providerId;
        private AuthProvider authProvider;
        private int likeCnt;
        private int helpfulCnt;
        private int understandCnt;
        private Long today;
        private Long total;
        private String avatarUrl;
        private String backgroundUrl;
        private String bgmUrl;
        private int isFamily;
        private List<BgmList> bgmList;
    }

    public static class UserSearchList{
        private List<SearchInfo> userSearchList;

        @Getter
        @Setter
        @Builder
        public static class SearchInfo{
            private Long userSeq;
            private String name;
            private String nickname;
            private String avatarUrl;
            private int isFamily;
        }
    }

    @Getter
    @Setter
    @Builder
    public static class UserMainInfo{
        private Long userSeq;
        private String email;
        private String name;
        private String nickname;
        private String comment;
        private String phoneNumber;
        private String providerId;
        private AuthProvider authProvider;
        private int likeCnt;
        private int helpfulCnt;
        private int understandCnt;
        private Long today;
        private Long total;
        private String avatarUrl;
        private String backgroundUrl;
        private int isFamily;
        private List<GrassResponse> grassList;
        private List<FamilyResponseDto> familyResponseDtos;
        private List<BgmList> bgmList;

        public void getFamilyResponseDtos(Object body) {
            this.familyResponseDtos = (List<FamilyResponseDto>) body;
        }

        public void getGrassList(Object body) {
            this.grassList = (List<GrassResponse>) body;
        }
    }
}
