package com.green.project_quadruaple.entity.model;

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
@Builder
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "profile_pic", length = 500)
    private String profilePic;

    @Column(length = 20, unique = true, nullable = false)
    private String name;

    @Column(length = 50, unique = true, nullable = false)
    private String email;

    @Column(length = 13, unique = true, nullable = false)
    private String phone;

    @Column(nullable = false)
    private LocalDate birth;

    @Column(length = 200, nullable = false)
    private String pw;

    @Column(nullable = false)
    private int state;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
}