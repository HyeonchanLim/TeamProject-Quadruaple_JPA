package com.green.project_quadruaple.trip.model.res;

import com.green.project_quadruaple.common.config.enumdata.ResponseCode;
import com.green.project_quadruaple.common.model.ResultResponse;
import com.green.project_quadruaple.trip.model.dto.TripDetailDto;
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
    private List<Long> tripUserIdList;
    private List<Long> tripLocationList;
    private List<TripDetailDto> days;
}
/*
* {
  "code" : "200 성공",
  "totalDistance" : 200102000,
  "totalDuration" : 420,
  "scheduleCnt" : 12,
  "memoCnt" : 8,
  "days":[
    {
      "day" : 1,
      "weather" : "cloudy" or null,
      "schedules" : [
        {
          "seq" : 1,
          "strfId" : 1,
          "strfTitle" : "차이나 타운",
          "category" : "관광",
          "address" : "인천 중구",
          "lat" : 37.5664707,
          "lng" : 127.0039404,
          "distance" : 13920 or null,
          "duration" : 35 or null,
          "pathtype" : "BUS" or null,
        },
        ...
      ]
   }
   ...
  ],
}
* */