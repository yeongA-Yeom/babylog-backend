package com.first.babylog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder // 객체 생성 전용 도우미
public class SocialLoginRequest {

    private String provider;

    private String providerId;

    private String email;
    private String birthYear;

    private String name;
}
