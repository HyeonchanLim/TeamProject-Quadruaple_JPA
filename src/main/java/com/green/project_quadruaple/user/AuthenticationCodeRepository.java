package com.green.project_quadruaple.user;

import com.green.project_quadruaple.entity.model.AuthenticationCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface AuthenticationCodeRepository extends JpaRepository<AuthenticationCode, Long> {
    Optional<AuthenticationCode> findByEmail(String email);
    boolean existsByEmailAndCodeNum(String email, String codeNum);
    int countByEmail(String email);
    int getByCodeNum(String codeNum);
    int deleteByEmail(String email);
}

