package com.green.project_quadruaple.entity.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "authentication_code")
@Getter
@Setter
@NoArgsConstructor
public class AuthenticationCode {
    @EmbeddedId
    private AuthenticationCodeId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "granted_at", nullable = false)
    private LocalDateTime grantedAt;
}
