package com.green.project_quadruaple.review.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ReviewPostReq {
    @JsonIgnore
    private Long reviewId;
    private String content;
    private int rating;
    private Long strfId;
}
