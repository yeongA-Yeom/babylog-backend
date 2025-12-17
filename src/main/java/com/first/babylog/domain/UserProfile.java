package com.first.babylog.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 사용자 프로필 엔티티
 * - 이름, 전화번호 등 개인정보 담당
 * - User와 1:1 관계
 */
@Getter
@Entity
@Table(name = "user_profiles")
public class UserProfile {

    /** users.num과 동일한 PK */
    @Id
    private Long userNum;

    /** User와 1:1 매핑 */
    @OneToOne
    @MapsId
    @JoinColumn(name = "user_num")
    private User user;

    /** 사용자 이름 */
    @Column(nullable = false)
    private String name;

    /** 생성 시각 */
    private LocalDateTime createdAt;

    /** 수정 시각 */
    private LocalDateTime updatedAt;

    protected UserProfile() {}

    /**
     * 프로필 생성자
     */
    public UserProfile(User user, String name) {
        this.user = user;
        this.name = name;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void changeName(String name){
        this.name = name;
        this.updatedAt = LocalDateTime.now();
    }
}
