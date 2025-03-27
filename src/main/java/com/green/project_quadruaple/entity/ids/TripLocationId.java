package com.green.project_quadruaple.entity.ids;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TripLocationId {
    private Long tripId;
    private Long locationId;
}
