package com.green.project_quadruaple.entity.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewPic {
    @EmbeddedId
    private ReviewPicId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("reviewId")
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;
}
