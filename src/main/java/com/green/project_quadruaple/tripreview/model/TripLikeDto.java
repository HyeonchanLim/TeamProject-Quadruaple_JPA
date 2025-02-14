package com.green.project_quadruaple.tripreview.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TripLikeDto {
    @JsonIgnore
    @Schema(example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private long userId;
    @Schema(example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private long tripReviewId;
}
