package com.first.babylog.service;


import com.first.babylog.domain.User;
import com.first.babylog.domain.UserEmail;
import com.first.babylog.domain.UserProfile;
import com.first.babylog.domain.UserSocialAccount;
import com.first.babylog.dto.SocialLoginRequest;
import com.first.babylog.repository.UserEmailRepository;
import com.first.babylog.repository.UserProfileRepository;
import com.first.babylog.repository.UserRepository;
import com.first.babylog.repository.UserSocialAccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserAuthService {

    private final UserRepository userRepository;
    private final UserSocialAccountRepository socialAccountRepository;
    private final UserProfileRepository userProfileRepository;
    private final UserEmailRepository userEmailRepository;
    private final KakaoOAuthService kakaoOAuthService;

    /**
     * 소셜 로그인 공통 처리
     * - provider: KAKAO / GOOGLE / APPLE
     * - providerId: 소셜 서비스 고유 ID
     */
    public User socialLogin(SocialLoginRequest request) {

        // 1️⃣ 이미 연결된 소셜 계정이 있는지 확인
        Optional<UserSocialAccount> socialOpt =
                socialAccountRepository.findByProviderAndProviderId(
                        request.getProvider(),
                        request.getProviderId()
                );

        // 2️⃣ 있으면 → 기존 회원 로그인
        if (socialOpt.isPresent()) {
            User user = socialOpt.get().getUser();

            // 마지막 로그인 시각 갱신
            user.updateLastLoginAt();

            return user;
        }

        // 3️⃣ 없으면 → 신규 회원 생성
        User newUser = User.createSocialUser(request.getProvider(), request.getProviderId());
        //프로필 생성
        UserProfile profile = UserProfile.create(request.getName());
        //연관 관계 설정
        newUser.assignProfile(profile);

        userRepository.save(newUser);

        if (request.getEmail() != null) {
            UserEmail email = UserEmail.create(newUser, request.getEmail());
            email.verify();
            email.markAsPrimary();
            userEmailRepository.save(email);
        }

        UserSocialAccount social = UserSocialAccount.connect(
                newUser,
                request.getProvider(),
                request.getProviderId()
        );

        socialAccountRepository.save(social);

        return newUser;

    }

    //회원 탈퇴
    @Transactional
    public void unlinkKakaoAndWithdraw(User user, String accessToken) {

        // 1) 카카오 연결 해제 (외부)
        kakaoOAuthService.unlink(accessToken);

        // 2) 우리 서비스 DB 정리 (연결 정보 삭제)
        socialAccountRepository.deleteByUser(user);
        userEmailRepository.deleteByUser(user);
        userProfileRepository.deleteByUser(user);

        // 3) 사용자 삭제
        userRepository.delete(user);
    }

}
