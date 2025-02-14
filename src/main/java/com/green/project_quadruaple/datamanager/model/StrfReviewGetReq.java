package com.green.project_quadruaple.datamanager.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@ToString
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class StrfReviewGetReq {
    private String category;
    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String strfTitle;
    private String picFolder;
    private String content;
    private int rating;
    private long userId;
    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long startId;
    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long endId;
}
