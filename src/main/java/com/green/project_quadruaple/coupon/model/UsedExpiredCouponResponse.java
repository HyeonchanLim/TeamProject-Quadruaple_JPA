package com.green.project_quadruaple.coupon.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class UsedExpiredCouponResponse {
    private long userId;
    private int usedCouponCount;
    private int expiredCouponCount;
    private List<UsedCouponDto> usedCoupons;
    private List<ExpiredCouponDto> expiredCoupons;
}
