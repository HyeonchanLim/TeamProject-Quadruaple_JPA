package com.green.project_quadruaple.strf.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class StrfTime {
    private long strfId;
    private String busiNum;
    @Schema(description = "클로즈 체크아웃 시간 (HH:mm 형식)", example = "18:00")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime openCheckIn;
    @Schema(description = "클로즈 체크아웃 시간 (HH:mm 형식)", example = "18:00")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime closeCheckOut;
}
