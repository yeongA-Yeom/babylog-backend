package com.first.babylog.repository;

import com.first.babylog.domain.User;
import com.first.babylog.domain.UserSocialAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserSocialAccountRepository extends JpaRepository<UserSocialAccount, Long> {

    void deleteByUser(User user);

    Optional<UserSocialAccount> findByProviderAndProviderId(
            String provider,
            String providerId
    );
}

