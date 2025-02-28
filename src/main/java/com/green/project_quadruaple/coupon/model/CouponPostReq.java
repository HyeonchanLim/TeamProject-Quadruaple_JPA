package com.green.project_quadruaple.coupon.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class CouponPostReq {
    private String title;
    private LocalDateTime expiredAt;
    private int discountPer;
    private LocalDateTime distributeAt;
    private Long strfId;
}
