package com.green.project_quadruaple.trip.model.res;

import com.green.project_quadruaple.common.config.enumdata.ResponseCode;
import com.green.project_quadruaple.common.model.ResultResponse;
import com.green.project_quadruaple.trip.model.dto.TripDetailDto;
import com.green.project_quadruaple.trip.model.dto.TripLocationDto;
import com.green.project_quadruaple.trip.model.dto.TripUserDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class TripDetailRes {

    private long totalDistance;
    private long totalDuration;
    private int scheduleCnt;
    private int memoCnt;
    private Long tripId;
    private String title;
    private String startAt;
    private String endAt;
    private List<TripUserDto> tripUserIdList;
    private List<TripLocationDto> tripLocationList;
    private List<TripDetailDto> days;
}
