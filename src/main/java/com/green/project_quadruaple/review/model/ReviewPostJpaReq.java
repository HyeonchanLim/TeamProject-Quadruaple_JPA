package com.green.project_quadruaple.review.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.green.project_quadruaple.entity.model.StayTourRestaurFest;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ReviewPostJpaReq {
    @JsonIgnore
    private Long reviewId;
    private String content;
    private int rating;
    private Long strfId;
}
