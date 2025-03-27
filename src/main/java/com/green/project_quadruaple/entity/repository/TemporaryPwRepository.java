package com.green.project_quadruaple.entity.repository;

import com.green.project_quadruaple.entity.model.TemporaryPw;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TemporaryPwRepository extends JpaRepository<TemporaryPw, Long> {
    Optional<TemporaryPw> findByUserId(Long userId);
}
