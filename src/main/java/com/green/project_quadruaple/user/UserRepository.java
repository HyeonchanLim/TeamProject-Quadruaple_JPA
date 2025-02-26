package com.green.project_quadruaple.user;

import com.green.project_quadruaple.common.config.security.SignInProviderType;
import com.green.project_quadruaple.entity.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = "authenticationCode")
    User findByAuthenticationCode_EmailAndProviderType(String email, SignInProviderType providerType);

    Optional<User> findByAuthenticationCode_Email(String email);

    boolean existsByAuthenticationCode_Email(String email);

    boolean existsByName(String name);

    Optional<User> findByAuthenticationCode_AuthenticatedId(Long authenticatedId);
}
