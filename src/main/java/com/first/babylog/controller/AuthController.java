package com.first.babylog.controller;

import com.first.babylog.dto.SignUpRequest;
import com.first.babylog.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // JSON 데이터를 반환하는 컨트롤러임을 명시
@RequestMapping("/api/auth") // 이 클래스의 모든 API는 주소 앞에 /api/auth가 붙음
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // 회원가입 API
    // 요청 주소: POST http://localhost:8080/api/auth/signup
    // @RequestBody: 프론트에서 보낸 JSON 데이터를 SignUpRequest 객체로 변환해서 받음
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody SignUpRequest request) {
        // 서비스의 회원가입 로직 호출
        authService.signUp(request);

        // 성공 시 200 OK 상태코드와 메시지 반환
        return ResponseEntity.ok("회원가입 성공!");
    }
}