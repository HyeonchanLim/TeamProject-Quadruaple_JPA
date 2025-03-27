package com.green.project_quadruaple.entity.model;

import com.green.project_quadruaple.entity.ids.TripLikeId;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TripLike {
    @EmbeddedId
    private TripLikeId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("tripReviewId")
    @JoinColumn(name = "trip_review_id")
    private TripReview tripReviewId;

}
