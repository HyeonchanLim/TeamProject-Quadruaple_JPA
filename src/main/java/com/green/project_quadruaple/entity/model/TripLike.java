package com.green.project_quadruaple.entity.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TripLike {

    @Id
    @Column(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User userId;

    @Column(name = "trip_review_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private TripReview tripReviewId;

    public TripLike(User userId, TripReview tripReviewId) {
        this.userId = userId;
        this.tripReviewId = tripReviewId;
    }
}
