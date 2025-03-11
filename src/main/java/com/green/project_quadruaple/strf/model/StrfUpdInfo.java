package com.green.project_quadruaple.strf.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class StrfUpdInfo {
    private long strfId;
    private String busiNum;
    private String category;



    private int state;



    private String detail;
    private LocalDate startAt;
    private LocalDate endAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime openCheckIn;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime closeCheckOut;
    private String tell;
    private String title;
    private String address;
    private long locationDetailId;
    private double lat;
    private double lng;
    private String post;
    private List<String> restdates;
}
