package com.green.project_quadruaple.coupon;

import com.green.project_quadruaple.common.config.enumdata.ResponseCode;
import com.green.project_quadruaple.common.model.ResponseWrapper;
import com.green.project_quadruaple.coupon.model.CouponResponse;
import com.green.project_quadruaple.coupon.model.UsedExpiredCouponResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("coupon")
@Tag(name = "쿠폰")
public class CouponController {
    private final CouponService couponService;

    // 사용 가능한 쿠폰 조회
    @GetMapping("available-coupons")
    @Operation(summary = "사용 가능한 쿠폰")
    public ResponseEntity<?> getCoupon() {
        CouponResponse coupon = couponService.getCoupon();

        if (coupon == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(ResponseCode.NOT_FOUND.getCode(), null));
        }
        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), coupon));
    }

    //만료된 쿠폰, 사용한 쿠폰 조회
    @GetMapping("expired-coupons")
    @Operation(summary = "만료된 쿠폰, 사용한 쿠폰 조회")
    public ResponseEntity<?> getExpiredCoupon() {
        UsedExpiredCouponResponse usedExpiredCouponResponse = couponService.getUsedExpiredCoupon();

        if (usedExpiredCouponResponse == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(ResponseCode.NOT_FOUND.getCode(), null));
        }
        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), usedExpiredCouponResponse));
    }
}
