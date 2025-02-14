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
public class TripDetailDto {
    private int day;
    private String weather;
    private List<ScheduleResDto> schedules;
}

/*
* "days":[
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
* */