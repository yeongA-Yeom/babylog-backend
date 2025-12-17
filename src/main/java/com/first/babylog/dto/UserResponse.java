package com.first.babylog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 사용자 조회 응답 DTO
 * - 화면에 보여줘도 되는 정보만 포함
 */
@Getter
@AllArgsConstructor
public class UserResponse {

    private Long userNum;   // users.num
    private String loginId; // 로그인 아이디
    private String email;   // 대표 이메일
    private String name;    // 사용자 이름
}
