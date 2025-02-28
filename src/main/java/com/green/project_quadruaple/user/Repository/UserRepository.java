package com.green.project_quadruaple.user.Repository;

import com.green.project_quadruaple.common.config.security.SignInProviderType;
import com.green.project_quadruaple.entity.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = "authenticationCode")
    User findByAuthenticationCode_EmailAndProviderType(String email, SignInProviderType providerType);

    Optional<User> findByAuthenticationCode_Email(String email);

    boolean existsByAuthenticationCode_Email(String email);

    boolean existsByName(String name);

    Optional<User> findByAuthenticationCode_AuthenticatedId(Long authenticatedId);

    @Query("SELECT u FROM User u JOIN u.authenticationCode ac WHERE ac.email = :email")
    Optional<User> findByEmail(String email);

    String findNameById(Long id);
}
