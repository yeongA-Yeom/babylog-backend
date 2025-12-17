package com.first.babylog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.first.babylog.domain.User;

import java.util.Optional;

/**
 * User Repository
 * - User 엔티티 접근
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /** 아이디 조회 */
    Optional<User> findByLoginId(String loginId);



    /** 로그인 아이디 중복 체크 */
    boolean existsByLoginId(String loginId);
}
