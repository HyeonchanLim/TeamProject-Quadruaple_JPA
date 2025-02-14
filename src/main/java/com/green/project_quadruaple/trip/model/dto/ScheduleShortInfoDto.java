package com.green.project_quadruaple.trip.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ScheduleShortInfoDto {

    private long scheduleId;
    private String seq;
    private long strfId;
    private double lat;
    private double lng;

}
