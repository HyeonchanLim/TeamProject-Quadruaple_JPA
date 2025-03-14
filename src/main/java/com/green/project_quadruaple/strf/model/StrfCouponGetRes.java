package com.green.project_quadruaple.strf.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class StrfCouponGetRes {
    private long couponId;
    private String couponName;
    private LocalDateTime expiredAt;
    private int discountPer;
    private LocalDateTime distributeAt;
}
