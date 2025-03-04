package com.green.project_quadruaple.entity.model;

import com.green.project_quadruaple.entity.base.CreatedAt;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Amenipoint extends CreatedAt {

    @EmbeddedId
    private AmenipointId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("amenityId")
    @JoinColumn(name = "amenity_id", nullable = false)
    private Amenity amenity;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("strfId")
    @JoinColumn(name = "strf_id", nullable = false, columnDefinition = "CHAR(10) DEFAULT 0")
    private StayTourRestaurFest stayTourRestaurFest;
}
