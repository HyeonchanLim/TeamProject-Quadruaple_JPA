package com.green.project_quadruaple.report.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.green.project_quadruaple.entity.base.ReportEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@ToString
@Getter
@Setter
public class ReportPostByUserDto {
    @Schema(example="리뷰 상품 여행기")
    private String category;

    @Schema(example="1")
    private long reportTarget;

    @Schema(example = "이유")
    private String reason;
}
