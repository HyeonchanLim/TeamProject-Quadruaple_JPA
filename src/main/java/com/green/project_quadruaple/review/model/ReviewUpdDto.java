package com.green.project_quadruaple.review.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReviewUpdDto {
    private long reviewId;
    private long userId;
    private String content;
    private int rating;
}
