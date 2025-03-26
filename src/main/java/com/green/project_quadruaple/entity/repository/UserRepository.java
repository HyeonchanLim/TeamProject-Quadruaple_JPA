package com.green.project_quadruaple.entity.repository;

import com.green.project_quadruaple.common.config.jwt.UserRole;
import com.green.project_quadruaple.common.config.security.SignInProviderType;
import com.green.project_quadruaple.entity.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
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

    @Query("SELECT u.name from User u where u.userId = :id")
    String findNameById(Long id);

    @Query("""
        select u from User u
        join BusinessNum bn on u.userId = bn.user.userId
        join StayTourRestaurFest strf on bn.busiNum = strf.busiNum.busiNum
        where strf.strfId = :strfId
        """)
    User findByStrfId(Long strfId);

    @Query("select u from User u join Role r on r.user.userId=u.userId where r.role = :role")
    List<User> findByRole(UserRole role);

    @Query("select u from User u join Role r on r.user.userId=u.userId where r.role != :role")
    List<User> findNotInRole(UserRole role);
}
