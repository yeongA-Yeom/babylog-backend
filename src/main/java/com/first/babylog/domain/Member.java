package com.first.babylog.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

// @Entity: JPA가 관리하는 객체임을 명시 (DB 테이블과 1:1 매핑)
@Entity
// @Table: DB에 생성될 실제 테이블 이름을 'members'로 지정 (user는 예약어라 피함)
@Table(name = "members")
// @Getter: 모든 필드의 조회 메서드(getId, getEmail 등) 자동 생성
@Getter
// @NoArgsConstructor: JPA 사용을 위한 기본 생성자 생성 (접근 권한은 Protected로 제한하여 안전성 확보)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// @AllArgsConstructor: 모든 필드를 넣는 생성자 생성 (@Builder 사용 시 필요)
@AllArgsConstructor
// @Builder: 객체 생성 시 new Member(...) 대신 직관적인 빌더 패턴 사용 가능하게 함
@Builder
public class Member {

    // @Id: 이 필드가 테이블의 PK(기본 키)임을 명시
    @Id
    // @GeneratedValue: PK 값을 DB가 자동으로 1씩 증가시켜 생성 (Auto Increment)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id") // 실제 DB 컬럼명은 member_id로 지정
    private Long id;

    // --- 로그인 정보 ---
    // unique = true: 중복된 아이디 가입 방지
    @Column(length = 50, unique = true)
    private String username; // 일반 로그인 아이디

    @Column(length = 255)
    private String password; // 암호화된 비밀번호 (카카오 로그인 시 null일 수 있음)

    // --- 개인 정보 ---
    // nullable = false: 필수 입력 값
    @Column(nullable = false, length = 50)
    private String name; // 실명

    @Column(nullable = false, length = 50)
    private String nickname; // 별명 (앱 내 표시용)

    @Column(nullable = false, length = 100, unique = true)
    private String email; // 이메일 (중복 확인 및 로그인 식별자)

    private LocalDate babyDueDate; // 출산 예정일 (D-Day 계산용)

    // columnDefinition: DB 컬럼의 타입을 직접 지정 (TINYINT 1은 boolean과 매핑됨)
    // 기본값은 1 (true, 임신 중)
    @Column(columnDefinition = "TINYINT(1) DEFAULT 1")
    private Boolean isPregnant; // 임신 여부

    // --- SNS 연동 정보 ---
    // @Builder.Default: 빌더 패턴 사용 시 값을 안 넣으면 이 기본값이 들어감
    @Builder.Default
    private String loginType = "GENERAL"; // 가입 유형 (GENERAL:일반, KAKAO:카카오)

    @Builder.Default
    private String role = "ROLE_USER"; // 권한 (기본은 일반 유저)

    @Builder.Default
    private String status = "ACTIVE"; // 계정 상태 (ACTIVE:활성, DELETED:탈퇴)

    // @CreationTimestamp: 데이터가 저장될 때 시간 자동 기록
    @CreationTimestamp
    private LocalDateTime createdAt;

    // @UpdateTimestamp: 데이터가 수정될 때 시간 자동 갱신
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}