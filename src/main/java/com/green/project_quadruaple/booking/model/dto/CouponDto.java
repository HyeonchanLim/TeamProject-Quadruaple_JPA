package com.green.project_quadruaple.booking.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CouponDto {

    private Long receiveId;
    private Integer discountRate;
    private Long usedCouponId;

}
