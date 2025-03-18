package com.green.project_quadruaple.coupon.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class CouponDto {
    private String couponId;
    private String title;
    private LocalDate expiredAt;
    private int discountPer;
    private LocalDate distributeAt;
    private long daysLeft;
}
