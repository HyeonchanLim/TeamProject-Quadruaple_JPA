package com.green.project_quadruaple.entity.ids;

import com.green.project_quadruaple.common.config.jwt.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RoleId implements Serializable {

    @Column(name = "role", nullable = false)
    private UserRole role;
    @Column(name = "user_id", nullable = false)
    private Long userId;
}
