package com.green.project_quadruaple.user;

import com.green.project_quadruaple.entity.model.AuthenticationCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface AuthenticationCodeRepository extends JpaRepository<AuthenticationCode, Long> {
    Optional<AuthenticationCode> findByEmail(String email);

    Optional<AuthenticationCode> findByEmailAndCodeNum(String email, String codeNum);

    int countByEmailAndGrantedAtBetween(String email, LocalDateTime startTime, LocalDateTime endTime);

    int deleteByEmailAndGrantedAtBetween(String email, LocalDateTime startTime, LocalDateTime endTime);
}

