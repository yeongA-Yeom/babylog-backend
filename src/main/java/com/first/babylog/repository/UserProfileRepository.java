package com.first.babylog.repository;

import com.first.babylog.domain.User;
import com.first.babylog.domain.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * UserProfile 전용 Repository
 */
public interface UserProfileRepository
        extends JpaRepository<UserProfile, Long> {

    Optional<UserProfile> findByUser(User user);

    //회원 탈퇴
    void deleteByUser(User user);

}

