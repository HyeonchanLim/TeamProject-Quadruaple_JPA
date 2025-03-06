package com.green.project_quadruaple.strf.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.green.project_quadruaple.entity.model.*;
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
    private String cid;
    private String category;
    private String title;
    private double lat;
    private double lng;
    private String address;
    private long locationDetailId;
    private String post;
    private String tell;
    private LocalDate startAt;
    private LocalDate endAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime openCheckIn;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime closeCheckOut;
    private String detail;
    private String busiNum;
    private int state;
    private List<Long> amenipoints;
    private List<String> restdates;
    private List<StrfMenu> menus;
    private List<StrfParlor> parlors;
    private List<Long> rooms;
}

















