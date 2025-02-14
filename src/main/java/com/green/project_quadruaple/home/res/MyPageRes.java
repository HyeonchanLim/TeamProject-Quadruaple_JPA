package com.green.project_quadruaple.home.res;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class MyPageRes {
    private String name;
    private String profilePic;
    private int couponCnt;
    private List<UserTripDto> tripList;
}
