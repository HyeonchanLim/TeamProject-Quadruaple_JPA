package com.green.project_quadruaple.trip.model.dto;

import lombok.*;

import java.util.List;

@Getter
@RequiredArgsConstructor
@ToString
public class WeatherDto {
    private final List<WeatherItem> item;
}

/*
* {
   "response":{
      "header":{
         "resultCode":"00",
         "resultMsg":"NORMAL_SERVICE"
      },
      "body":{
         "dataType":"JSON",
         "items":{

         },
         "pageNo":1,
         "numOfRows":1000,
         "totalCount":8
      }
   }
}
* */