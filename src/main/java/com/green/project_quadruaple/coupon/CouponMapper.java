package com.green.project_quadruaple.coupon;

import com.green.project_quadruaple.coupon.model.CouponDto;
import com.green.project_quadruaple.coupon.model.CouponResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CouponMapper {
    // 쿠폰 조회
    List<CouponDto> findCouponsByUserId(long userId);

    // 사용한 쿠폰 조회
    List<CouponDto> selUsedCoupon(long userId);
}
