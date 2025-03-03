package com.green.project_quadruaple.coupon.admin;

import com.green.project_quadruaple.common.config.enumdata.ResponseCode;
import com.green.project_quadruaple.common.model.ResponseWrapper;
import com.green.project_quadruaple.coupon.model.CouponAdminGetDto;
import com.green.project_quadruaple.coupon.model.CouponAdminPostReq;
import com.green.project_quadruaple.coupon.model.CouponAdminUpdateDto;
import com.green.project_quadruaple.coupon.model.CouponBusinessPostReq;
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
@RequestMapping("coupon/admin/issuance")
@Tag(name = "쿠폰")
public class CouponAdminIssuanceController {
    private final CouponAdminIssuanceService couponAdminIssuanceService;

    @PostMapping
    @Operation(summary = "관리자 쿠폰 발급")
    public ResponseEntity<?> postCoupon(@RequestBody CouponAdminPostReq req) {
        int result = couponAdminIssuanceService.insCoupon(req);

        if (result < 0) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ResponseWrapper<>(ResponseCode.BAD_GATEWAY.getCode(), 0));
        }
        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), result));
    }

    @GetMapping()
    @Operation(summary = "관리자 쿠폰 조회")
    public ResponseEntity<?> getCouponAdmin() {
        List<CouponAdminGetDto> getCoupon = couponAdminIssuanceService.getCouponsByUser();

        if (getCoupon.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(ResponseCode.NOT_FOUND.getCode(), 0));
        }
        return ResponseEntity.ok(getCoupon);
    }

    @PatchMapping
    @Operation(summary = "관리자 쿠폰 수정")
    public ResponseEntity<?> updCoupon(@RequestBody CouponAdminUpdateDto dto) {
        int result = couponAdminIssuanceService.updCoupon(dto);

        if (result < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseWrapper<>(ResponseCode.BAD_GATEWAY.getCode(), 0));
        }
        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), result));
    }

    @GetMapping("count_all")
    @Operation(summary = "관리자 쿠폰 총 개수 조회")
    public ResponseEntity<?> countAdminCouponAll() {
        int result = couponAdminIssuanceService.countAdminCoupon();
        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), result));
    }
}
