package com.green.project_quadruaple.search.model.strf_list;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StrfShortInfoDto {

    private long strfId;
    private String category;
    private String title;
    private double lat;
    private double lng;
    private String picTitle;
    private int ratingCnt;
    private String explain;
    private int wishCnt;
    private float avgRating;
    private boolean wishIn;
    private boolean ratingIn;
    private String startAt;
    private String endAt;
}

/*
* {
        "strfId" : 1,
        "title" : "숙소명",
        "latit" : "37.5664707",
        "longitude" : "127.0039404",
        "picTitle" : "숙소.jpg",
        "ratingCnt" : 2000,
        "explain" : "숙소에 대한 간략한 설명",
        "wishCnt" : 150,
        "averageRating" : 3.2,
        "wishIn" : false,
        "ratingIn" : true,
        "startAt" : null,
        "endAt" : null
      } ,
* */
