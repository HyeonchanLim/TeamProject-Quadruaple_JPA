package com.green.project_quadruaple.review.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@Schema(title = "피드 등록 응답")
@ToString
@EqualsAndHashCode
public class ReviewPostRes {
    private long reviewId;
    private List<String> pics;

}
