package com.green.project_quadruaple.coupon.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpiredCouponDto {
    private Long couponId;
    private String title;
    private LocalDateTime expiredAt;
    private int discountPer;
}
