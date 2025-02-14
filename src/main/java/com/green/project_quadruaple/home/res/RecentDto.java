package com.green.project_quadruaple.home.res;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RecentDto {
    private long strfId;
    private String strfTitle;
    private String strfPic;
    private String locationName;
    private boolean wishIn;
    private double averageRating;
    private int wishCnt;
}
