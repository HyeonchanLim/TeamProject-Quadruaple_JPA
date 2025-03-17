package com.green.project_quadruaple.review.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Schema(title = "리뷰 정보")
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class MyReviewSelRes {
    private List<MyReviewSelResDto> dtoList;

    private boolean isMore;
}

