package com.first.babylog.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * UserSocialAccount 엔티티
 *
 * - 소셜 로그인 계정과 서비스 사용자(User)를 연결하는 테이블
 * - 하나의 User는 여러 소셜 계정을 가질 수 있음 (KAKAO, GOOGLE 등)
 * - 하나의 소셜 계정(provider + providerId)은 반드시 하나의 User에만 연결됨
 */
@Entity
@Getter
@Table(
        name = "user_social_account",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"provider", "provider_id"}
                )
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 🔒 외부 new 금지
public class UserSocialAccount {

    /**
     * 소셜 계정 PK
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long socialId;

    /**
     * 서비스 사용자 (N:1)
     * - User.userNum 과 매핑
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_num", nullable = false)
    private User user;

    /**
     * 소셜 로그인 제공자
     * 예) KAKAO, NAVER, GOOGLE, APPLE
     */
    @Column(nullable = false, length = 20)
    private String provider;

    /**
     * 소셜 서비스에서 내려주는 고유 사용자 ID
     * 예) 카카오 id, 구글 sub
     */
    @Column(name = "provider_id", nullable = false)
    private String providerId;

    /**
     * 소셜 계정 최초 연결 시각
     */
    @Column(name = "connected_at", nullable = false, updatable = false)
    private LocalDateTime connectedAt;

    /**
     * 🔐 내부 생성자
     * → 반드시 User와 함께 생성되도록 강제
     */
    private UserSocialAccount(User user, String provider, String providerId) {
        this.user = user;
        this.provider = provider;
        this.providerId = providerId;
        this.connectedAt = LocalDateTime.now();
    }

    /**
     * ✅ 소셜 계정 연결 팩토리 메서드
     */
    public static UserSocialAccount connect(
            User user,
            String provider,
            String providerId
    ) {
        return new UserSocialAccount(user, provider, providerId);
    }
}
