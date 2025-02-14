package com.green.project_quadruaple.review.model;

import com.green.project_quadruaple.common.model.Paging;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Schema(title = "리뷰 요청")
public class MyReviewSelReq extends Paging {
    @NotNull
    @Positive
    @Schema(title = "상품 PK",description = "상품 PK", example = "1")
//    @JsonProperty("user_id")
    private long userId;
    public MyReviewSelReq(Integer page, Integer size , long userId) {
        super(page, size);
        this.userId = userId;
    }
}