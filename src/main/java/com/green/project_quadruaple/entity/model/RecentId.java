package com.green.project_quadruaple.entity.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@EqualsAndHashCode
public class RecentId implements Serializable {
    private Long userId;
    private Long strfId;
}
