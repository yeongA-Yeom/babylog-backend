package com.first.babylog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // 비밀번호 암호화 기계(PasswordEncoder)를 스프링 빈으로 등록
    // BCrypt는 가장 널리 쓰이는 강력한 해시 암호화 방식
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 개발 단계: CSRF 비활성화
                .csrf(csrf -> csrf.disable())

                // 접근 허용 경로
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/**",                // 메인
                                "/login",           // 로그인
                                "/signup",          // 회원가입 페이지
                                "/users",           // 회원가입 API
                                "/css/**",          // css
                                "/js/**",           // js
                                "/images/**",        // 이미지
                                "/oauth/**",        // 카카오톡
                                "/favicon.ico"
                        ).permitAll()
                        .anyRequest().authenticated()
                )

                // 기본 보안 방식 제거
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable());

        return http.build();
    }
}
