package com.green.project_quadruaple.trip.model.dto;

import com.green.project_quadruaple.trip.model.Category;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ScheduleDto {

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
    private String title;
    private String content;
    private Long reviewed;
    private boolean isNotFirst;
    private Long nextScheduleId;
    private Long prevScheduleStrfId;
    private Long nextScheduleStrfId;
}