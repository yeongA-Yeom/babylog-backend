package com.first.babylog.dto;

import com.first.babylog.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter // 데이터를 꺼내 쓸 수 있게 Getter 생성
@NoArgsConstructor // 기본 생성자 생성 (JSON을 객체로 바꿀 때 필요)
public class SignUpRequest {
    // 프론트엔드에서 보내주는 JSON 필드명과 일치해야 함
    private String username;
    private String email;
    private String password;
    private String name;
    private String nickname;

    // 필수 아님 (프론트에서 값을 안 보내면 null로 들어옴)
    private LocalDate babyDueDate; // 출산 예정일 (형식: "yyyy-MM-dd")

    // [수정됨] 사용자가 선택할 수 있도록 필드 추가 (필수 아님, 없으면 true)
    private Boolean isPregnant;

    // DTO를 DB에 저장할 Entity로 변환하는 메서드
    // 서비스 로직에서 지저분한 변환 코드를 줄이기 위해 여기에 작성
    public Member toEntity(String encodedPassword) {
        return Member.builder()
                .username(this.username)
                .email(this.email)
                .password(encodedPassword) // 중요: 비밀번호는 반드시 암호화된 것을 받아야 함
                .name(this.name)
                .nickname(this.nickname)
                .babyDueDate(this.babyDueDate) // 값이 없으면 null로 들어감
                .role("ROLE_USER")    // 가입 시 기본 권한
                .status("ACTIVE")     // 가입 시 계정 상태
                .loginType("GENERAL") // 일반 이메일 가입
                // [수정됨] 사용자가 값을 보냈으면 그 값을, 안 보냈으면(null) 기본값 true 설정
                .isPregnant(this.isPregnant != null ? this.isPregnant : true)
                .build();
    }
}