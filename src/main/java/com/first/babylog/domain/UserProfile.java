package com.first.babylog.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 사용자 프로필 엔티티
 * - 이름, 전화번호 등 개인정보 담당
 * - User와 1:1 관계
 */
@Getter
@Entity
@Table(name = "user_profiles")
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 🔒 외부 생성 차단
public class UserProfile {

    /** users.user_num과 동일한 PK */
    @Id
    @Column(name = "user_num")
    private Long userNum;

    /** User와 1:1 매핑 */

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_num")
    private User user;

    @Version
    private long version;

    /**
     * 사용자 이름
     */
    @Column(nullable = false)
    private String name;

    /**
     * 전화번호
     */
    private String phone;

    /**
     * 생년월일
     */
    private LocalDate birthDate;

    /**
     * 성별
     */
    private String gender;

    /** 생성 시각 */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /** 수정 시각 */
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    /**
     * 🔐 User와 함께 생성하도록 강제
     */
    private UserProfile(User user) {
        this.user = user;
        this.userNum = user.getUserNum();
    }

    /**
     * ✅ 프로필 생성 팩토리 메서드
     */
    public static UserProfile create(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("UserProfile name is required");
        }
        UserProfile profile = new UserProfile();
        profile.name =name;
        return profile;
    }

    void assignUser(User user){
        this.user = user;
        this.userNum = user.getUserNum();
    }

    /**
     * 프로필 정보 업데이트
     * (필요한 것만 열어둠)
     */
    public void updateProfile(String name, String phone,
                              LocalDate birthDate, String gender) {
        this.name = name;
        this.phone = phone;
        this.birthDate = birthDate;
        this.gender = gender;
    }

    public void changeName(String name){
        //추후 구현 예정
    }


}
