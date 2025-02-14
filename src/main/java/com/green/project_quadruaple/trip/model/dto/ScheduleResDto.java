package com.green.project_quadruaple.trip.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ScheduleResDto {

    private long scheduleMemoId;
    private String scheOrMemo;
    private int seq;
    private Long distance;
    private Long duration;
    private Long strfId;
    private String strfTitle;
    private String category;
    private String address;
    private String lat;
    private String lng;
    private Integer pathType;
    private long tripId;
    private String content;
    private boolean reviewed;
    private String picName;
}