package com.green.project_quadruaple.entity.model;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ReviewPicId {
    @Column(length = 100)
    private String title;

    private Long reviewId;
}
