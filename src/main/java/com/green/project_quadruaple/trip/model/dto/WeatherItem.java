package com.green.project_quadruaple.trip.model.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public class WeatherItem {
    private final String baseDate;
    private final String baseTime;
    private final String category;
    private final String fcstDate;
    private final String fcstTime;
    private final String fcstValue;
    private final Integer nx;
    private final Integer ny;
}

/*
* "item":[
                  "baseDate":"20250204",
                  "baseTime":"0500",
                  "category":"TMP",
                  "fcstDate":"20250204",
                  "fcstTime":"0600",
                  "fcstValue":"-13",
                  "nx":55,
                  "ny":127
* */