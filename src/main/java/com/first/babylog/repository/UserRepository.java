package com.first.babylog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.first.babylog.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email); // JPA 규칙: findBy + 필드명


}
