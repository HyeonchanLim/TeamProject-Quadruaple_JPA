package com.green.project_quadruaple.trip.model.req;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode
public class PostScheduleReq {

    @JsonIgnore
    private long scheMemoId;

    @NotNull
    @JsonProperty("strf_id")
    @Schema(title = "상품 ID", type = "long", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private long strfId;

    @NotNull
    @JsonProperty("trip_id")
    @Schema(title = "여행 ID", type = "long", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private long tripId;

    @NotNull
    @Schema(title = "일정 순서", type = "int", example = "4", requiredMode = Schema.RequiredMode.REQUIRED)
    private final int seq;

    @NotNull
    @Schema(title = "여행 일차", type = "int", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
    private final int day;

    @Schema(title = "총 이동 시간", type = "int", example = "200", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private final Integer time;

    @Schema(title = "총 이동 거리", type = "int", example = "120000", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private final Integer distance;

    @JsonProperty("path_type")
    @Schema(title = "이동 수단", type = "string", example = "버스", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String pathType;

    @Setter
    @JsonIgnore
    private int pathTypeValue;

}
