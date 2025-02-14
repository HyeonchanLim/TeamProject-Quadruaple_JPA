package com.green.project_quadruaple.strf.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Schema
public class StrfSelRes {

    private Long strfId;
    private String category;
    private String strfTitle;
    private double latit;
    private double longitude;
    private String address;
    private String post;
    private String tell;
    private String startAt;
    private String endAt;
    private String openCheck;
    private String closeCheck;
    private String restDate;
    private String explain;
    private String detail;
    private String busiNum;
    private String locationName;
    private double ratingAvg;
    private String reviewWriterName;
    private String hostProfilePic;
    private String reviewWriterUserPic;
    private String hostName;
    private int wishCnt;
    private int wishIn;
    private int recentCheck;
    private int recentCheckStatus;
    private String inquiredAt;
    private String reviewCnt;



    private List<StrfAmenity> amenities;
    private List<StrfPicSel> strfPics;
    private List<Menu> menu;

}
