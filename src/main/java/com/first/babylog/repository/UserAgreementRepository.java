package com.first.babylog.repository;

import com.first.babylog.domain.UserAgreement;
import com.first.babylog.domain.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAgreementRepository extends JpaRepository<UserAgreement, Long> {}
