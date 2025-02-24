package com.green.project_quadruaple.entity.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class RecentTripReview {

    @Id
    @Column(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User userId;

    @Column(name = "trip_review_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private TripReview tripReviewId;

    @Column(name = "inquired_at", nullable = false)
    private LocalDateTime inquiredAt;

    public RecentTripReview(User userId, TripReview tripReviewId) {
        this.userId = userId;
        this.tripReviewId = tripReviewId;
        this.inquiredAt = LocalDateTime.now(); // 현재 시간으로 기본값 설정
    }
}
