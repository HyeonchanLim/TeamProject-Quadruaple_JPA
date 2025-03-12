package com.green.project_quadruaple.entity.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RecentTr {
    @EmbeddedId
    private RecentTrId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("tripReviewId")
    @JoinColumn(name = "trip_review_id")
    private TripReview tripReviewId;

    @Column(name = "inquired_at", nullable = false)
    private LocalDateTime inquiredAt;

    @Column(name = "undo_recent", nullable = false,columnDefinition = "TINYINT(4) DEFAULT 0")
    private int undoRecent;
}
