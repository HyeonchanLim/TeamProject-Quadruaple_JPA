package com.green.project_quadruaple.trip.model.req;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class PostTripReq {

    @NotNull
    @JsonIgnore
    private long tripId;

    @NotBlank
    @Schema(title = "여행 제목", type= "string", example = "여행 제목 입니다.", requiredMode = Schema.RequiredMode.REQUIRED)
    private String title;

    @NotBlank
    @JsonProperty("location_id")
    @Schema(title = "여행 지역 ID", type= "list", example = "[1,2]", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Long> locationId;

    @NotBlank
    @JsonProperty("start_at")
    @Schema(title = "여행 시작 일자", type= "string", example = "2025-01-01", requiredMode = Schema.RequiredMode.REQUIRED)
    private String startAt;

    @NotBlank
    @JsonProperty("end_at")
    @Schema(title = "여행 완료 일자", type= "string", example = "2025-01-11", requiredMode = Schema.RequiredMode.REQUIRED)
    private String endAt;

    @JsonIgnore
    private long managerId;
}
