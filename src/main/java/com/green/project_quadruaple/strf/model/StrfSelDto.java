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
public class StrfSelDto {
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
    private String detail;
    private String busiNum;
    private String locationName;
    private int state;
    private String cid;
    private String hostProfilePic;
    private String hostName;
    private String inquiredAt;
    private int wishCnt;
    private Double ratingAvg;
    private String reviewCnt;
    private int wishIn;
    private int recentCheck;
    private int recentCheckStatus;

    private List<StrfPicSel> strfPics;
    private List<Integer> restDate;

}
