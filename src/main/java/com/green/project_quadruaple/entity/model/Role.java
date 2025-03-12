package com.green.project_quadruaple.entity.model;

import com.green.project_quadruaple.common.config.enumdata.UserRoleConverter;
import com.green.project_quadruaple.common.config.jwt.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class Role {

    @EmbeddedId
    private RoleId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id", insertable = false, nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Convert(converter = UserRoleConverter.class)
    @Column(name = "role", insertable = false, updatable = false)
    private UserRole role;

    private LocalDateTime grantedAt;
}
