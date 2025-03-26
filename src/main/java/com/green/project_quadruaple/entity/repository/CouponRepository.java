package com.green.project_quadruaple.entity.repository;

import com.green.project_quadruaple.entity.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
    @Query("SELECT c FROM Coupon c JOIN ReceiveCoupon r ON c.couponId = r.coupon.couponId WHERE r.user.userId=:userId")
    List<Coupon> findCouponsByUserId(Long userId);

    @Query("SELECT c FROM Coupon c JOIN ReceiveCoupon rc ON c.couponId = rc.coupon.couponId " +
            "WHERE rc.user.userId = :userId AND c.expiredAt < :expiredAt")
    List<Coupon> findExpiredCouponsByUserId(Long userId, LocalDateTime expiredAt);

    int countAllByStrfIsNull();

    int countByStrfBusiNumUserUserId(Long userId);

    @Query(value = "SELECT * FROM coupon c WHERE DATEDIFF(c.expired_at, NOW()) = 3", nativeQuery = true)
    List<Coupon> findExpireBeforeCoupon();

}
