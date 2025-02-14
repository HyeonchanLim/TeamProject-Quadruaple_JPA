package com.green.project_quadruaple.trip.model.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class TripDto {

    private long tripId;
    private String title;
    private String startAt;
    private String endAt;
    private String locationPic;
    private int scheduleCnt;

}
