package com.first.babylog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                // CSRF 끄기 (개발 단계)
                .csrf(csrf -> csrf.disable())

                // 요청 허용 범위
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",                 // 홈
                                "/error",            // 에러 페이지 ⭐ 중요
                                "/js/**",
                                "/css/**",
                                "/images/**",
				"/login"
                        ).permitAll()
                        .anyRequest().permitAll()   // ⭐ 지금 단계에서는 전부 허용
                )

                // 로그인 화면 안 쓸 거라 비활성
                .formLogin(form -> form.disable());
//                .httpBasic(basic -> basic.disable())


        return http.build();}
    }
