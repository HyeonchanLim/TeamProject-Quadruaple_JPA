package com.green.project_quadruaple.entity.model;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleId implements java.io.Serializable {
    private String role;
    private Long userId;
}
