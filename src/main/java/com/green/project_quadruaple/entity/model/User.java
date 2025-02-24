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
public class User extends CreatedAt {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false)
    private SignInProviderType providerType;

    @Column(name = "profile_pic", length = 500)
    private String profilePic;

    @Column(length = 20, unique = true, nullable = false)
    private String name;

    @Column(length = 50, unique = true, nullable = false)
    private String email;

    @Column
    private LocalDate birth;

    @Column(name = "pw", nullable = false, length = 200)
    private String password;

    @Column(nullable = false, columnDefinition = "TINYINT DEFAULT 0")
    private int state;

    @Column(nullable = false, columnDefinition = "TINYINT DEFAULT 0")
    private int verified;

    @Column(name = "tell")
    private String tell;

    public User(SignInProviderType providerType, String profilePic, String name, String email, LocalDate birth, String password, String tell) {
        this.providerType = providerType;
        this.profilePic = profilePic;
        this.name = name;
        this.email = email;
        this.birth = birth;
        this.password = password;
        this.tell = tell;
    }
}