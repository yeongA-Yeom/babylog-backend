package com.first.babylog.domain;

/**
 * 사용자 권한(enum)
 * - USER  : 일반 사용자
 * - ADMIN : 관리자
 *
 * 👉 권한은 값이 거의 변하지 않으므로 enum이 적합
 */
public enum Role {
    USER,
    ADMIN
}
