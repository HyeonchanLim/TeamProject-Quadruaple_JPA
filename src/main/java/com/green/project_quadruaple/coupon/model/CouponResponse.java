package com.green.project_quadruaple.coupon.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class CouponResponse {
    private long userId;
    private int availableCouponCount;
    private List<CouponDto> coupons;
}
