package com.green.project_quadruaple.trip.model.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ScheCntAndMemoCntDto {

    private int scheduleCnt;
    private int memoCnt;
    private Long tripId;
    private String title;
    private String startAt;
    private String endAt;
    private List<Long> tripLocationList;
}
