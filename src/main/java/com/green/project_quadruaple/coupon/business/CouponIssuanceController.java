package com.green.project_quadruaple.coupon.business;

import com.green.project_quadruaple.common.config.enumdata.ResponseCode;
import com.green.project_quadruaple.common.model.ResponseWrapper;
import com.green.project_quadruaple.coupon.model.CouponGetDto;
import com.green.project_quadruaple.coupon.model.CouponPostReq;
import com.green.project_quadruaple.coupon.model.CouponUpdateDto;
import com.green.project_quadruaple.entity.model.Coupon;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("coupon/issuance")
@Tag(name = "쿠폰")
public class CouponIssuanceController {
    private final CouponIssuanceService couponIssuanceService;

    @PostMapping
    @Operation(summary = "사업자 쿠폰 발급")
    public ResponseEntity<?> insCoupon(@RequestBody CouponPostReq req) {
        int result = couponIssuanceService.insCoupon(req);

        if (result < 0) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ResponseWrapper<>(ResponseCode.BAD_GATEWAY.getCode(), 0));
        }
        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), result));
    }

    @GetMapping("business")
    @Operation(summary = "사업자 쿠폰 조회")
    public ResponseEntity<?> getCoupon() {
        List<CouponGetDto> getCoupon = couponIssuanceService.getCouponsByUser();

        if (getCoupon.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(ResponseCode.NOT_FOUND.getCode(), 0));
        }
        return ResponseEntity.ok(getCoupon);
    }

    @PatchMapping
    @Operation(summary = "사업자 쿠폰 수정")
    public ResponseEntity<?> updateCoupon(@RequestBody CouponUpdateDto req) {
        int result = couponIssuanceService.updCoupon(req);
        if (result < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseWrapper<>(ResponseCode.BAD_GATEWAY.getCode(), 0));
        }
        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), result));
    }
}
