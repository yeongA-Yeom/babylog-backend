package com.first.babylog.service;

import com.first.babylog.domain.Member;
import com.first.babylog.dto.SignUpRequest;
import com.first.babylog.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service // 스프링에게 "이 클래스는 비즈니스 로직을 담당해"라고 알려줌
@RequiredArgsConstructor // final이 붙은 필드들의 생성자를 자동 생성 (의존성 주입)
public class AuthService {

    private final MemberRepository memberRepository; // DB 작업용

    private final PasswordEncoder passwordEncoder;   // 비밀번호 암호화용 (SecurityConfig에 정의됨)
    @Transactional // 이 메서드가 실행되는 동안 에러가 나면 DB 변경사항을 모두 취소(롤백)함
    public Long signUp(SignUpRequest request) {
        // 1. 이메일 중복 검사 (이미 가입된 이메일이면 에러 발생)
        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }

        // 2. 비밀번호 암호화 (보안 필수! 평문 저장은 불법)
        // 사용자가 입력한 "1234"를 "$2a$10$..." 같은 알 수 없는 문자로 변환
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // 3. 회원 저장
        // DTO를 Entity로 바꾸면서 암호화된 비밀번호를 같이 넣어줌
        // 여기서 request.toEntity()로 만든 객체는 id가 null이므로 INSERT가 실행됨
        Member savedMember = memberRepository.save(request.toEntity(encodedPassword));

        // 저장된 회원의 ID(PK) 반환
        return savedMember.getId();
    }
}
