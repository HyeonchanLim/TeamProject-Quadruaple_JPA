package com.green.project_quadruaple.businessmypage;

import com.green.project_quadruaple.businessmypage.model.*;
import com.green.project_quadruaple.common.config.enumdata.ResponseCode;
import com.green.project_quadruaple.common.model.ResponseWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("business/my-page")
@Tag(name = "사업자 마이페이지")
public class BusinessMyPageController {
    private final BusinessMyPageService businessMyPageService;

    @GetMapping("booking")
    @Operation(summary = "예약 현황(only STAY category)"
              ,description = "state 상태 = 0 : 대기중, 1 : 확정, 2 : 완료, 3 : 취소")
    public ResponseEntity<?> getBusinessMyPageBooking(@RequestParam(required = false, defaultValue = "2025-01-01") String startDate
                                                    , @RequestParam(required = false, defaultValue = "2025-12-31") String endDate) {
        List<BusinessMyPageBooking> result = businessMyPageService.selBusinessMyPageBooking(startDate, endDate);

        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(ResponseCode.NOT_FOUND.getCode(), null));
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping("booking/details")
    @Operation(summary = "예약 현황 상세 보기(only STAY category)"
              ,description = "bookingNum: 예약 인원/recomCapacity: 권장 인원/maxCapacity: 최대 인원/extraPersonCount: 초과 인원(예약 인원 - 권장 인원)/extraFee: 초과 인원당 금액")
    public ResponseEntity<?> getBusinessMyPageBookingDetails(Long bookingId) {
        BusinessMyPageBookingDetails result = businessMyPageService.selBusinessMyPageBookingDetails(bookingId);

        if (result == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(ResponseCode.NOT_FOUND.getCode(), null));
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping("sales")
    @Operation(summary = "매출 현황 조회", description = "조회할 시작 월과 종료 월을 yyyy-MM 형식으로 입력 (최대 12개월)")
    public ResponseEntity<?> getBusinessMyPageSales(
            @RequestParam String startMonth,
            @RequestParam String endMonth
    ) {
        List<BusinessMyPageSales> result = businessMyPageService.selBusinessMyPageSales(startMonth, endMonth);
        return ResponseEntity.ok(result);
    }

    @GetMapping("used-point")
    @Operation(summary = "결제된 포인트 사용 list 조회", description = "환불 안 함 = false, 환불 함 = true/totalAmount : 환불 한 포인트는 합계x")
    public ResponseEntity<?> getBusinessMyPageUsedPoint() {
        BusinessMyPagePointList result = businessMyPageService.selBusinessMyPagePointList();

        if (result == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(ResponseCode.NOT_FOUND.getCode(), null));
        }

        return ResponseEntity.ok(result);
    }
}
