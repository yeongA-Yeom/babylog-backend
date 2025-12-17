package com.first.babylog.domain;

/**
 * 사용자 계정 상태(enum)
 * 👉 "계정이 어떤 상태인가"를 명확히 표현
 */
public enum UserStatus {

    ACTIVE,     // 정상 사용 가능
    INACTIVE,   // 비활성 (휴면 등)
    BLOCKED,    // 제재/차단
    DELETED     // 탈퇴 (소프트 삭제)
}
