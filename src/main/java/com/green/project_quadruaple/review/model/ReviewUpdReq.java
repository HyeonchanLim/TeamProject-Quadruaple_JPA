package com.green.project_quadruaple.review.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class ReviewUpdReq {
    private long reviewId;
    private long userId; // 로그인한 사용자 ID
    private String content; // 리뷰 내용
    private int rating; // 평점
}