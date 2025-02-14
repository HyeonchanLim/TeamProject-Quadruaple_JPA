package com.green.project_quadruaple.booking.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ApproveData {

    private String userName;
    private String menuTitle;
    private String checkIn;
    private String checkOut;
    private int personnel;
}
/*
* 유저 데이터(이름)
메뉴(객실명, 입실일(시간 포함), 퇴실일(시간포함), 인원)
* */