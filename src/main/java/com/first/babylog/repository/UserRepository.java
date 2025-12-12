package com.first.babylog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.first.babylog.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UserRepository extends JpaRepository<User, Long> {
}
