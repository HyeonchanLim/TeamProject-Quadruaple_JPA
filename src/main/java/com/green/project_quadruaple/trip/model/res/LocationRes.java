package com.green.project_quadruaple.trip.model.res;

import com.green.project_quadruaple.common.config.enumdata.ResponseCode;
import com.green.project_quadruaple.common.model.ResultResponse;
import com.green.project_quadruaple.trip.model.dto.LocationDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class LocationRes extends ResultResponse {
    public LocationRes() {
        super(ResponseCode.OK.getCode());
    }

    private List<LocationDto> locationList;
}
