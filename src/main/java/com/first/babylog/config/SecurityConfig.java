package com.first.babylog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 개발 단계: CSRF 비활성화
                .csrf(csrf -> csrf.disable())

                // 접근 허용 경로
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",                // 메인
                                "/login",           // 로그인
                                "/signup",          // 회원가입 페이지
                                "/users",           // 회원가입 API
                                "/css/**",          // css
                                "/js/**",           // js
                                "/images/**"        // 이미지
                        ).permitAll()
                        .anyRequest().authenticated()
                )

                // 기본 보안 방식 제거
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable());

        return http.build();
    }
}
