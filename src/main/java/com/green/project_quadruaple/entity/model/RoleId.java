package com.green.project_quadruaple.entity.model;

import com.green.project_quadruaple.common.config.jwt.UserRole;
import jakarta.persistence.Embeddable;
import lombok.*;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class RoleId implements java.io.Serializable {
    private UserRole role;
    private Long userId;
}
