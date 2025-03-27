package com.green.project_quadruaple.entity.model;

import com.green.project_quadruaple.entity.ids.ScrapId;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Scrap {
    @EmbeddedId
    private ScrapId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("tripReviewId")
    @JoinColumn(name = "trip_review_id")
    private TripReview tripReviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("tripId")
    @JoinColumn(name = "trip_id")
    private Trip trip;
}

