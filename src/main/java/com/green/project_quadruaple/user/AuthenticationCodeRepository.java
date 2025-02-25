package com.green.project_quadruaple.user;

import com.green.project_quadruaple.entity.model.AuthenticationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AuthenticationCodeRepository extends JpaRepository<AuthenticationCode, Long> {
    List<AuthenticationCode> findByEmail(String email);
    Optional<AuthenticationCode> findFirstByEmailOrderByGrantedAtDesc(String email);
    boolean existsByEmailAndCodeNum(String email, String codeNum);
    int countByEmail(String email);
    int getByCodeNum(String codeNum);
    int deleteByEmail(String email);

    @Query("SELECT a.email FROM AuthenticationCode a GROUP BY a.email HAVING COUNT(a.email) >= 5")
    List<String> findEmailsWithFiveOrMoreCodes();

    Optional<AuthenticationCode> findByAuthenticatedId(Long authenticatedId);
}

