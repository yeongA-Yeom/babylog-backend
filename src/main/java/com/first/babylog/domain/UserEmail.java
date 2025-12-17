package com.first.babylog.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * 사용자 이메일 엔티티
 * - 이메일 주소 + 인증 상태 관리
 */
@Getter
@Entity
@Table(name = "user_emails")
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

    /** 대표 이메일 여부 */
    private boolean isPrimary = true;

    /** 인증 여부 */
    private boolean isVerified = false;

    /** 등록 시각 */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /** 인증 완료 시각 */
    private LocalDateTime verifiedAt;

    protected UserEmail() {}

    public UserEmail(User user, String email) {
        this.user = user;
        this.email = email;
        this.createdAt = LocalDateTime.now();
    }

    public void changeEmail(String email) {
        this.email = email;
        this.isVerified = false;
    }
}
