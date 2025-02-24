package com.green.project_quadruaple.user;

import com.green.project_quadruaple.entity.model.AuthenticationCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface AuthenticationCodeRepository extends JpaRepository<AuthenticationCode, Long> {
//    Optional<AuthenticationCode> findById_UserIdAndId_CodeNum(Long userId, String codeNum);
//    int countById_UserIdAndGrantedAtBetween(Long userId, LocalDateTime startTime, LocalDateTime endTime);
//    int deleteById_UserIdAndGrantedAtBetween(Long userId, LocalDateTime startTime, LocalDateTime endTime);
}

