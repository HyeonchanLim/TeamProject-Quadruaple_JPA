package com.green.project_quadruaple.user;

import com.green.project_quadruaple.common.config.security.SignInProviderType;
import com.green.project_quadruaple.entity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
//    User findByEmailAndProviderType(String email, SignInProviderType signInProviderType);
//    Optional<User> findByEmail(String email);

//    boolean existsByEmail(String email);
    boolean existsByName(String name);
}
