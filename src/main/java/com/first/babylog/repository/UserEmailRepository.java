package com.first.babylog.repository;

import com.first.babylog.domain.User;
import com.first.babylog.domain.UserEmail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * UserEmail Repository
 * - 이메일 중복 체크용
 */
public interface UserEmailRepository extends JpaRepository<UserEmail, Long> {

    boolean existsByEmail(String email);

    Optional<UserEmail> findByUser(User user);

    //회원 탈퇴
    void deleteByUser(User user);

}
