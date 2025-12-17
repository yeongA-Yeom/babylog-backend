package com.first.babylog.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * User 엔티티
 * - 로그인/인증/권한/상태만 책임
 * - 이름, 이메일 같은 개인정보는 ❌
 */
@Entity
@Getter
@Table(name = "users")
public class User {

    /**
     * 사용자 PK (AUTO_INCREMENT)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="userNum")
    private Long userNum;

    /**
     * 로그인 아이디 (이메일 아님)
     */
    @Column(name = "id", nullable = false, unique = true)
    private String loginId;

    /**
     * 암호화된 비밀번호
     */
    @Column(nullable = false)
    private String password;

    /**
     * 사용자 권한
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    /**
     * 계정 상태
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status;

    /**
     * 마지막 로그인 시각
     */
    private LocalDateTime lastLoginAt;

    /**
     * 비밀번호 변경 시각
     */
    private LocalDateTime passwordChangedAt;

    /**
     * 계정 생성 시각
     */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;


    /**
     * 계정 수정 시각
     */
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;



    /**
     * 탈퇴 시각 (소프트 삭제)
     */
    private LocalDateTime deletedAt;

    /**
     * JPA 기본 생성자
     */
    protected User() {
    }

    /**
     * 회원가입 시 사용하는 생성자
     * 👉 User가 책임지는 최소 정보만 받음
     */
    public User(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
        this.role = Role.USER;                 // 기본 권한
        this.status = UserStatus.ACTIVE;       // 기본 상태
    }
    // 비밀번호 변경 편의 메소드
    public void changePassword(String newPassword) {
        this.password = newPassword;
    }
}
