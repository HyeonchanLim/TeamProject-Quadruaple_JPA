package com.green.project_quadruaple.trip.model.res;

import com.green.project_quadruaple.common.config.enumdata.ResponseCode;
import com.green.project_quadruaple.common.model.ResultResponse;
import com.green.project_quadruaple.trip.model.dto.TripDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class MyTripListRes extends ResultResponse {

    public MyTripListRes() {
        super(ResponseCode.OK.getCode());
    }

    private List<TripDto> beforeTripList;
    private List<TripDto> afterTripList;
}
