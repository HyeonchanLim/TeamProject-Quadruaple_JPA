package com.green.project_quadruaple.trip.model.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.green.project_quadruaple.common.config.enumdata.ResponseCode;
import com.green.project_quadruaple.common.model.ResultResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostTripRes extends ResultResponse {

    public PostTripRes() {
        super(ResponseCode.OK.getCode());
    }

    @JsonProperty("trip_id")
    private long tripId;
}
