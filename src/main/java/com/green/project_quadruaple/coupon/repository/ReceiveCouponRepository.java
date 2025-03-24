package com.green.project_quadruaple.coupon.repository;

import com.green.project_quadruaple.entity.model.Coupon;
import com.green.project_quadruaple.entity.model.ReceiveCoupon;
import com.green.project_quadruaple.entity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReceiveCouponRepository extends JpaRepository<ReceiveCoupon, Long> {
    List<ReceiveCoupon> findByUser_UserId(Long userId);

    List<ReceiveCoupon> findByCoupon(Coupon coupon);

    ReceiveCoupon findByUser_UserIdAndCoupon_CouponId(long userId,long couponId);
}
