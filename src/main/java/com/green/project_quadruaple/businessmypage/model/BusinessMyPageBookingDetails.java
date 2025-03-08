package com.green.project_quadruaple.businessmypage.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
public class BusinessMyPageBookingDetails {
    private String menuTitle;
    private int roomNum;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private int bookingNum;
    private String name;
    private String tell;
    private String email;
    private int totalPayment;
    private String couponTitle;
    private int recomCapacity;
    private int maxCapacity;
    private int extraPersonCount;
    private int extraFee;
    private int usedPoint;
}
