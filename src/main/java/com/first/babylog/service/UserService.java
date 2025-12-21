package com.first.babylog.service;

import com.first.babylog.domain.User;
import com.first.babylog.domain.UserAgreement;
import com.first.babylog.domain.UserEmail;
import com.first.babylog.domain.UserProfile;
import com.first.babylog.dto.UserCreateRequest;
import com.first.babylog.dto.UserDto;
import com.first.babylog.dto.UserResponse;
import com.first.babylog.repository.UserAgreementRepository;
import com.first.babylog.repository.UserEmailRepository;
import com.first.babylog.repository.UserProfileRepository;
import com.first.babylog.repository.UserRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.first.babylog.dto.UserUpdateRequest;




import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 사용자 서비스
 * - 회원가입 비즈니스 로직 담당
 * - users / profiles / emails / agreements를 하나의 트랜잭션으로 처리
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final UserEmailRepository userEmailRepository;
    private final UserAgreementRepository userAgreementRepository;

    /** 비밀번호 암호화용 */
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(
            UserRepository userRepository,
            UserProfileRepository userProfileRepository,
            UserEmailRepository userEmailRepository,
            UserAgreementRepository userAgreementRepository
    ) {
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
        this.userEmailRepository = userEmailRepository;
        this.userAgreementRepository = userAgreementRepository;
    }

    /**
     * 회원가입 처리
     * @param request 회원가입 요청 DTO
     */
    @Transactional
    public void signUp(UserCreateRequest request) {

        // 1️⃣ 비밀번호 / 재입력 검증
        if (!request.getPassword().equals(request.getRePassword())) {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }

        // 2️⃣ 로그인 아이디 중복 체크
        if (userRepository.existsByLoginId(request.getLoginId())) {
            throw new IllegalStateException("이미 사용 중인 아이디입니다.");
        }

        // 3️⃣ 이메일 중복 체크
        if (userEmailRepository.existsByEmail(request.getEmail())) {
            throw new IllegalStateException("이미 사용 중인 이메일입니다.");
        }

        // 4️⃣ users 테이블 저장
        User user = User.signUp(request.getLoginId(), request.getPassword());
        UserProfile profile = UserProfile.create(request.getName());
        user.assignProfile(profile);
        userRepository.save(user);


        // 6️⃣ user_emails 테이블 저장
        UserEmail email = new UserEmail(
                user,
                request.getEmail()
        );
        userEmailRepository.save(email);

        // 7️⃣ user_agreements 테이블 저장 (필수 약관)
        UserAgreement agreement = new UserAgreement(
                user,
                "TERMS"
        );
        userAgreementRepository.save(agreement);
    }

    /**
     * 전체 사용자 조회
     */
    @Transactional(readOnly = true)
    public List<UserResponse> findAll() {

        return userRepository.findAll().stream()
                .map(user -> {
                    // 대표 이메일 조회
                    UserEmail email = userEmailRepository
                            .findAll().stream()
                            .filter(e -> e.getUser().equals(user))
                            .findFirst()
                            .orElseThrow(() -> new IllegalStateException("이메일 정보 없음"));

                    // 프로필 조회
                    UserProfile profile = userProfileRepository
                            .findByUser(user)
                            .orElseThrow(() -> new IllegalStateException("프로필 정보 없음"));

                    return new UserResponse(
                            user.getUserNum(),
                            user.getLoginId(),
                            email.getEmail(),
                            profile.getName()
                    );
                })
                .collect(Collectors.toList());
    }



    /**
     * 로그인 아이디 기준 사용자 단건 조회
     */
    @Transactional(readOnly = true)
    public UserResponse findByLoginId(String loginId) {

        // 1️⃣ 로그인 아이디로 User 조회
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalStateException("해당 아이디 사용자를 찾을 수 없습니다."));

        // 2️⃣ 이메일 조회
        UserEmail userEmail = userEmailRepository.findByUser(user)
                .orElseThrow(() -> new IllegalStateException("이메일 정보 없음"));

        // 3️⃣ 프로필 조회
        UserProfile profile = userProfileRepository.findByUser(user)
                .orElseThrow(() -> new IllegalStateException("프로필 정보 없음"));

        return new UserResponse(
                user.getUserNum(),      // PK
                user.getLoginId(),      // 로그인 아이디
                userEmail.getEmail(),   // 이메일
                profile.getName()       // 이름
        );
    }

    /**
     * 로그인 아이디 기준 사용자 정보 수정
     */
    @Transactional
    public void updateUser(String loginId, UserUpdateRequest request) {

        // 1️⃣ 로그인 아이디로 User 조회
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalStateException("해당 아이디 사용자를 찾을 수 없습니다."));

        // 2️⃣ 이름 변경 (프로필)
        if (request.getName() != null) {
            UserProfile profile = userProfileRepository.findByUser(user)
                    .orElseThrow(() -> new IllegalStateException("프로필 정보 없음"));

            profile.changeName(request.getName());
        }

        // 3️⃣ 이메일 변경
        if (request.getEmail() != null) {
            UserEmail userEmail = userEmailRepository.findByUser(user)
                    .orElseThrow(() -> new IllegalStateException("이메일 정보 없음"));

            userEmail.changeEmail(request.getEmail());
        }
    }

    public String createTempPassword(UserDto.FindPasswordRequest request) {

        // 1. 아이디로 User 찾기 (없으면 바로 에러)
        User user = userRepository.findByLoginId(request.getLoginId())
                .orElseThrow(() -> new IllegalArgumentException("일치하는 사용자 정보가 없습니다."));

        // 2. 해당 User의 이름 확인 (UserProfileRepository)
        UserProfile profile = userProfileRepository.findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("일치하는 사용자 정보가 없습니다."));

        if (!profile.getName().equals(request.getName())) {
            throw new IllegalArgumentException("일치하는 사용자 정보가 없습니다.");
        }

        // 3. 해당 User의 이메일 확인 (UserEmailRepository)
        UserEmail userEmail = userEmailRepository.findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("일치하는 사용자 정보가 없습니다."));

        if (!userEmail.getEmail().equals(request.getEmail())) {
            throw new IllegalArgumentException("일치하는 사용자 정보가 없습니다.");
        }

        // 4. 모든 정보가 일치함 -> 임시 비밀번호 생성 및 변경
        String tempPassword = UUID.randomUUID().toString().substring(0, 8); // 8자리 랜덤 비번
        user.changePassword(passwordEncoder.encode(tempPassword));

        // (JPA의 영속성 컨텍스트가 자동으로 변경 감지하여 DB 업데이트를 수행합니다.
        // 혹시 업데이트가 안 된다면 userRepository.save(user); 를 추가하세요)

        return tempPassword;
    }
}
