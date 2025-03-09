package com.green.project_quadruaple.coupon.repository;

import com.green.project_quadruaple.booking.model.dto.DiscountPerDto;
import com.green.project_quadruaple.entity.model.UsedCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UsedCouponRepository extends JpaRepository<UsedCoupon, Long> {
    @Query("SELECT uc FROM UsedCoupon uc JOIN uc.receiveCoupon rc WHERE rc.user.userId = :userId")
    List<UsedCoupon> findUsedCouponByUserId(Long userId);

    List<UsedCoupon> findByReceiveCoupon_User_UserId(Long userId);

    @Query("""
        select uc from UsedCoupon uc
        where uc.booking.bookingId = :bookingId
        """)
    List<UsedCoupon> findByBookingId(Long bookingId);
}
