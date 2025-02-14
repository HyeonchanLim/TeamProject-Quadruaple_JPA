package com.green.project_quadruaple.recent.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class RecentGetListRes {
    private Long strfId;
    private Long userId;
    private String strfTitle;
    private String strfPic;
    private String category;
    private String locationName;
    private int price;
    private int reviewCnt;
    private int ratingAvg;
    private int wishCnt;
    private int wishIn;
    private String inquiredAt;
}
