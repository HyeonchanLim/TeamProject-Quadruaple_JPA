package com.green.project_quadruaple.strf.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.green.project_quadruaple.entity.model.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class StrfInsReq {
    private String category;
    private String title;
    private double lat;
    private double lng;
    private String address;
    private String locationTitle;
    private String post;
    private String tell;
    private LocalDate startAt;
    private LocalDate endAt;
    private String busiNum;
    @Schema(description = "오픈 체크인 시간 (HH:mm 형식)", example = "09:00")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime openCheckIn;

    @Schema(description = "클로즈 체크아웃 시간 (HH:mm 형식)", example = "18:00")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime closeCheckOut;
    private String detail;
    private int state;

    private List<String> restdates;
}

















