package com.project.StoryModule.dto;


import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.NotEmpty;

public class UserRequestDto {

    @Getter
    @Setter
    public static class SignIn{

        @NotEmpty(message = "이메일을 입력해야 합니다.")
        private String email;

        @NotEmpty(message = "비밀번호를 입력해야 합니다.")
        private String password;

        public UsernamePasswordAuthenticationToken toAuthentication(){
            return new UsernamePasswordAuthenticationToken(email, password);
        }
    }

    @Getter
    @Setter
    public static class SignUp{
        private String nickname;
        private String email;
        private String password;
        private String name;
    }

    @Getter
    @Setter
    public static class Reissue{

        @NotEmpty(message = "accessToken을 입력해야 합니다.")
        private String accessToken;

        @NotEmpty(message = "refreshToken을 입력해야 합니다.")
        private String refreshToken;
    }
}
