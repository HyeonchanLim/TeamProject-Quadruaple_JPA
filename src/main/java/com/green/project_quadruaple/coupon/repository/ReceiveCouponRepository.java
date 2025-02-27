package com.green.project_quadruaple.coupon.repository;

import com.green.project_quadruaple.entity.model.ReceiveCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReceiveCouponRepository extends JpaRepository<ReceiveCoupon, Long> {
    List<ReceiveCoupon> findByUser_UserId(Long userId);
}
