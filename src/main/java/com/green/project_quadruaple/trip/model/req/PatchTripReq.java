package com.green.project_quadruaple.trip.model.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.beans.ConstructorProperties;
import java.util.List;

@Getter
@Setter
@ToString
public class PatchTripReq {

    @NotNull
    @Schema(title = "여행 ID", type = "long", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("trip_id")
    private long tripId;

    @Schema(title = "수정 여행 타이틀", type = "string", example = "여행 타이틀 수정", requiredMode = Schema.RequiredMode.REQUIRED)
    private String title;

    @JsonProperty("start_at")
    @Schema(title = "수정 여행 시작 일자", type = "string", example = "2025-02-01", requiredMode = Schema.RequiredMode.REQUIRED)
    private String startAt;

    @JsonProperty("end_at")
    @Schema(title = "수정 여행 완료 일자", type = "string", example = "2025-02-07", requiredMode = Schema.RequiredMode.REQUIRED)
    private String endAt;

    @JsonProperty("ins_user_list")
    @Schema(title = "여행 구성원 추가", type = "list", example = "[1,2,3]", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Long> insUserList;

    @JsonProperty("del_user_list")
    @Schema(title = "여행 구성원 삭제", type = "list", example = "[4,5,6]", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Long> delUserList;

    @JsonProperty("ins_location_list")
    @Schema(title = "여행 지역 추가", type = "list", example = "[1,2]", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Long> insLocationList;

    @JsonProperty("del_location_list")
    @Schema(title = "여행 지역 삭제", type = "list", example = "[4,5]", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Long> delLocationList;
}