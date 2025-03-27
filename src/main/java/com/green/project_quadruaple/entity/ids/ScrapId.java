package com.green.project_quadruaple.entity.ids;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ScrapId implements Serializable {
    private Long tripReviewId;
    private Long tripId;
}
