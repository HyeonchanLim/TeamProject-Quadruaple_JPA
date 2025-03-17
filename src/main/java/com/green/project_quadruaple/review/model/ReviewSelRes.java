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
public class ReviewSelRes{
        private List<ReviewSelResDto> dtoList;

        private boolean isMore;

}
