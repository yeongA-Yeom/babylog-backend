package com.first.babylog.controller;

import com.first.babylog.domain.User;
import com.first.babylog.dto.KakaoUserResponse;
import com.first.babylog.dto.SocialLoginRequest;
import com.first.babylog.repository.UserRepository;
import com.first.babylog.service.KakaoOAuthService;
import com.first.babylog.service.UserAuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class KakaoAuthController {

    private final KakaoOAuthService kakaoOAuthService;
    private final UserAuthService userAuthService;
    private final UserRepository userRepository;

    /**
     * 1️⃣ 카카오 로그인 시작
     * - 카카오 로그인 화면으로 리다이렉트
     */
    @GetMapping("/kakao")
    public void kakaoLogin(HttpServletResponse response) throws IOException {
        String kakaoAuthUrl = kakaoOAuthService.getAuthorizeUrl();
        response.sendRedirect(kakaoAuthUrl);
    }

    /**
     * 2️⃣ 카카오 로그인 콜백
     * - 인가 코드 수신
     * - access_token 발급
     * - 사용자 정보 조회
     * - 서비스 로그인 처리
     */
    @GetMapping("/kakao/callback")
    public String kakaoCallback(
            @RequestParam("code") String code,
            HttpSession session
    ) {

        // 1. access_token 발급
        String accessToken = kakaoOAuthService.getAccessToken(code);

        // 2. 카카오 사용자 정보 조회
        KakaoUserResponse kakaoUser = kakaoOAuthService.getUserInfo(accessToken);

        /*
         * kakaoUser 예시
         * {
         *   id=4649440357,
         *   kakao_account={
         *     email=xxx@xxx.com,
         *     birthyear=1995
         *   }
         * 카카오 사용자 정보: {
         * id=4649440357,
         * kakao_account={
         *      has_email=true,
         *      email_needs_agreement=false,
         *      is_email_valid=true,
         *      is_email_verified=true,
         *      email=duddk9503@naver.com,
         *      has_birthyear=true,
         *      birthyear_needs_agreement=false,
         *      birthyear=1995,
         *      has_birthday=true,
         *      birthday_needs_agreement=false,
         *      birthday=0310,
         *      birthday_type=SOLAR,
         *      is_leap_month=false,
         *      name=염영아
         * }}
         * }
         */

           // 3. 서비스 로그인 요청 DTO 생성
        SocialLoginRequest request = SocialLoginRequest.builder()
                .provider("KAKAO")
                .providerId(String.valueOf(kakaoUser.getId()))
                .email(kakaoUser.getKakaoAccount().getEmail())
                .name(String.valueOf(kakaoUser.getKakaoAccount().getName()))
                .build();


        // 4. 로그인/회원가입 처리
        User loginUser = userAuthService.socialLogin(request);

        // 5. 세션 저장 (로그인 처리)
        session.setAttribute("LOGIN_USER", loginUser.getUserNum());
        session.setAttribute("KAKAO_ACCESS_TOKEN",accessToken);

        return "redirect:/";
    }

    /**
     * 3️⃣ 로그아웃
     * - 세션만 종료
     * - (카카오 로그아웃은 별도 버튼에서 처리)
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
    @GetMapping("/logout/kakao")
    public String kakaoLogout(HttpSession session) {
        String kakaoAccessToken = (String) session.getAttribute("KAKAO_ACCESS_TOKEN");

        if(kakaoAccessToken != null){
            kakaoOAuthService.logout(kakaoAccessToken);
        }
        session.invalidate();
        return "redirect:/";
    }

    //회원탈퇴 or 테스트용 로그아웃
    @GetMapping("/kakao/unlink")
    public String unlinkTest(HttpSession session) {
        Long userNum = (Long) session.getAttribute("LOGIN_USER");
        String accessToken = (String) session.getAttribute("KAKAO_ACCESS_TOKEN");

        User user = userRepository.findById(userNum).orElseThrow();
        userAuthService.unlinkKakaoAndWithdraw(user, accessToken);

        session.invalidate();
        return "redirect:/";
    }



    /**
     * 카카오 응답에서 이메일 추출
     */
    private String extractEmail(Map<String, Object> kakaoUser) {
        Object accountObj = kakaoUser.get("kakao_account");
        if (accountObj == null) return null;

        Map<String, Object> account = (Map<String, Object>) accountObj;
        return (String) account.get("email");
    }
}
