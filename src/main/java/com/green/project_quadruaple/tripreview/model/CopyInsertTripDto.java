package com.green.project_quadruaple.tripreview.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CopyInsertTripDto {
    @JsonIgnore
    private long tripId;
    private long tripReviewId;
    private long copyTripId;
    @JsonIgnore
    private long userId;
    @Schema(title = "여행 시작 일자", type= "string", example = "2025-01-01", requiredMode = Schema.RequiredMode.REQUIRED)
    private String newStartAt;
    @Schema(title = "여행 완료 일자", type= "string", example = "2025-01-11", requiredMode = Schema.RequiredMode.REQUIRED)
    private String newEndAt;

}
