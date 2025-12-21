package com.first.babylog.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * 사용자 이메일 엔티티
 * - 이메일 주소 + 인증 상태 관리
 */
@Getter
@Entity
@Table(name = "user_emails")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEmail {

    /** 이메일 PK */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long emailId;

    /** 소속 사용자 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_num")
    private User user;

    /** 이메일 주소 */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * 대표 이메일 여부
     * - 한 유저당 하나만 true
     */
    @Column(name = "is_primary", nullable = false)
    private boolean primaryEmail;

    /**
     * 이메일 인증 여부
     */
    @Column(name = "is_verified", nullable = false)
    private boolean verified;

    /** 등록 시각 */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /** 인증 완료 시각 */
    private LocalDateTime verifiedAt;



    public UserEmail(User user, String email) {
        this.user = user;
        this.email = email;
        this.primaryEmail = false;
        this.verified = false;
    }
    /**
     * ✅ 이메일 생성 팩토리 메서드
     */
    public static UserEmail create(User user, String email) {
        UserEmail userEmail = new UserEmail();
        userEmail.user = user;
        userEmail.email = email;
        userEmail.primaryEmail = false;
        userEmail.verified = false;
        return userEmail;
    }

    /**
     * 이메일 인증 처리
     */
    public void verify() {
        this.verified = true;
        this.verifiedAt = LocalDateTime.now();
    }

    /**
     * 대표 이메일로 설정
     */
    public void markAsPrimary() {
        this.primaryEmail = true;
    }

    /**
     * 대표 이메일 해제
     */
    public void unmarkPrimary() {
        this.primaryEmail = false;
    }

    public void changeEmail(String email) {
    }
}
