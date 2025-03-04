package com.green.project_quadruaple.entity.model;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TripLikeId implements Serializable {
    private Long tripReviewId;
    private Long userId;
}
