package com.green.project_quadruaple.coupon.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsedCouponDto {
    private Long receiveId;
    private Long couponId;
    private String title;
    private LocalDate expiredAt;
    private int discountPer;
}
