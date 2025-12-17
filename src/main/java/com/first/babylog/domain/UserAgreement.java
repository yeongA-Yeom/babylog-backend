package com.first.babylog.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * 사용자 약관 동의 엔티티
 * - 법적 이력 관리용
 */
@Entity
@Table(name = "user_agreements")
public class UserAgreement {

    /** 약관 동의 PK */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long agreementId;

    /** 소속 사용자 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_num")
    private User user;

    /** 약관 타입 (TERMS, PRIVACY 등) */
    @Column(nullable = false)
    private String agreementType;

    /** 동의 여부 */
    private boolean isAgreed;

    /** 동의 시각 */
    private LocalDateTime agreedAt;

    protected UserAgreement() {}

    public UserAgreement(User user, String agreementType) {
        this.user = user;
        this.agreementType = agreementType;
        this.isAgreed = true;
        this.agreedAt = LocalDateTime.now();
    }
}
