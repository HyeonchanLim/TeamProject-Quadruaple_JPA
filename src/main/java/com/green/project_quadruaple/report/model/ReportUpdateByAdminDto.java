package com.green.project_quadruaple.report.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class ReportUpdateByAdminDto {
    @Schema(example = "1")
    private long reportId;

    @Schema(example = "이유")
    private String processed;
}
