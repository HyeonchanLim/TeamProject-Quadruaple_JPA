package com.green.project_quadruaple.review.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ReviewDelReq {
    private Long userId;
    private Long reviewId;
}
