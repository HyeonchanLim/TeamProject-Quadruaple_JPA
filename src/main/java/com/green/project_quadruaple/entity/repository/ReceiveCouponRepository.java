package com.green.project_quadruaple.entity.repository;

import com.green.project_quadruaple.entity.model.Coupon;
import com.green.project_quadruaple.entity.model.ReceiveCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReceiveCouponRepository extends JpaRepository<ReceiveCoupon, Long> {
    List<ReceiveCoupon> findByUser_UserId(Long userId);

    List<ReceiveCoupon> findByCoupon(Coupon coupon);

    ReceiveCoupon findByUser_UserIdAndCoupon_CouponId(long userId,long couponId);
}
