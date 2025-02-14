package com.green.project_quadruaple.trip.model.req;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PostStrfScheduleReq {

    @JsonIgnore
    private long scheMemoId;

    @JsonProperty("strf_id")
    @Schema(title = "상품 ID", type = "long", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private long strfId;

    @JsonProperty("trip_id")
    @Schema(title = "여행 ID", type = "long", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private long tripId;

    @Schema(title = "여행 일차", type = "int", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
    private int day;
}
