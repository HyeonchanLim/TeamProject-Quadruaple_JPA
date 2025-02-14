package com.green.project_quadruaple.coupon.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class CouponDto {
    private String couponId;
    private String title;
    private LocalDateTime expiredAt;
    private int discountPer;
    private LocalDateTime distributeAt;
}
