package com.green.project_quadruaple.trip.model.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class PatchSeqReq {

    @NotNull
    @Schema(title = "여행 ID", type = "long", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private long tripId;

    @NotNull
    @Schema(title = "일정 ID", type = "long", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private long scheduleId;

    @NotNull
    @Schema(title = "기존 day", type = "int", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private int originDay;

    @Schema(title = "변경된 후의 day", type = "int", example = "2", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer destDay;

    @NotNull
    @Schema(title = "기존 seq", type = "int", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private int originSeq;

    @NotNull
    @Schema(title = "변경된 후의 seq", type = "int", example = "3", requiredMode = Schema.RequiredMode.REQUIRED)
    private int destSeq;
}
