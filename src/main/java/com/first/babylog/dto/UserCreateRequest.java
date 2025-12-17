package com.first.babylog.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 회원가입 요청 DTO
 * - 프론트에서 전달받는 데이터 묶음
 * - 검증은 Service에서 수행
 */
@Getter
@Setter
public class UserCreateRequest {

    private String loginId;     // 로그인 아이디
    private String password;    // 비밀번호
    private String rePassword;  // 비밀번호 확인
    private String email;       // 이메일
    private String name;        // 이름
}
