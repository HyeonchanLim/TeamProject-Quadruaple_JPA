package com.green.project_quadruaple.entity.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

