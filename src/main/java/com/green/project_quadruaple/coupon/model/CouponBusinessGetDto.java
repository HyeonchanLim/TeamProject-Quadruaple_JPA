package com.green.project_quadruaple.coupon.model;

import com.green.project_quadruaple.entity.model.Coupon;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@EqualsAndHashCode
public class CouponBusinessGetDto {
    private Long couponId;
    private String title;
    private LocalDateTime expiredAt;
    private int discountPer;
    private LocalDateTime distributeAt;
    private Long strfId;

    // Coupon 객체를 받아서 필드를 초기화하는 생성자
    public CouponBusinessGetDto(Coupon coupon) {
        this.couponId = coupon.getCouponId();
        this.title = coupon.getTitle();
        this.expiredAt = coupon.getExpiredAt();
        this.discountPer = coupon.getDiscountPer();
        this.distributeAt = coupon.getDistributeAt();
        this.strfId = coupon.getStrf().getStrfId();
    }
}
