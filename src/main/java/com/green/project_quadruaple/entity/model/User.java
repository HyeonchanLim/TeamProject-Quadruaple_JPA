package com.green.project_quadruaple.entity.model;

import com.green.project_quadruaple.common.config.security.SignInProviderType;
import com.green.project_quadruaple.entity.base.CreatedAt;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends CreatedAt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "authenticated_id", nullable = false)
    private AuthenticationCode authenticationCode;

    @Column(name = "provider_type", nullable = false)
    private SignInProviderType providerType;

    @Column(name = "profile_pic", length = 500)
    private String profilePic;

    @Column(length = 20, unique = true, nullable = false)
    private String name;

    @Column
    private LocalDate birth;

    @Column(name = "pw", nullable = false, length = 200)
    private String password;

    @Column(nullable = false, columnDefinition = "TINYINT(4) DEFAULT 0")
    private int state;

    @Column(nullable = false, columnDefinition = "TINYINT(4) DEFAULT 0")
    private int verified;

    @Column(length = 20, unique = true)
    private String tell;

}