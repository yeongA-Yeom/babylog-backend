package com.first.babylog.dto;

import lombok.Getter;
import lombok.Setter;

public class UserDto {

    // 비밀번호 찾기용
    @Getter @Setter
    public static class FindPasswordRequest {
        private String loginId;
        private String email;
        private String name;
    }


}
