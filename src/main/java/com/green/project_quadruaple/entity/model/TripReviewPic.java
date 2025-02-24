package com.green.project_quadruaple.entity.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TripReviewPic {

    @Id
    @Column(name = "trip_review_pic", length = 200)
    private String tripReviewPic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_review_id", nullable = false)
    private TripReview tripReview;

    public TripReviewPic(String tripReviewPic, TripReview tripReview) {
        this.tripReviewPic = tripReviewPic;
        this.tripReview = tripReview;
    }
}
