package com.green.project_quadruaple.businessmypage;

import com.green.project_quadruaple.businessmypage.model.BusinessMyPageBooking;
import com.green.project_quadruaple.businessmypage.model.BusinessMyPageBookingDetails;
import com.green.project_quadruaple.businessmypage.model.BusinessMyPageSales;
import com.green.project_quadruaple.common.config.enumdata.ResponseCode;
import com.green.project_quadruaple.common.model.ResponseWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("business/my-page")
@Tag(name = "사업자 마이페이지")
public class BusinessMyPageController {
    private final BusinessMyPageService businessMyPageService;

    @GetMapping("booking")
    @Operation(summary = "예약 현황")
    public ResponseEntity<?> getBusinessMyPageBooking() {
        List<BusinessMyPageBooking> result = businessMyPageService.selBusinessMyPageBooking();

        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(ResponseCode.NOT_FOUND.getCode(), null));
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping("booking/details")
    @Operation(summary = "예약 현황 상세 보기"
            , description = "bookingNum: 예약 인원/recomCapacity: 권장 인원/maxCapacity: 최대 인원/extraPersonCount: 초과 인원(예약 인원 - 권장 인원)/extraFee: 초과 인원당 금액")
    public ResponseEntity<?> getBusinessMyPageBookingDetails(Long bookingId) {
        BusinessMyPageBookingDetails result = businessMyPageService.selBusinessMyPageBookingDetails(bookingId);

        if (result == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(ResponseCode.NOT_FOUND.getCode(), null));
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping("sales")
    @Operation(summary = "매출 현황 조회", description = "orderType엔 1, 3, 6, 12 만 입력")
    public ResponseEntity<?> getBusinessMyPageSales(@RequestParam(defaultValue = "1") Integer orderType) {
        BusinessMyPageSales result = businessMyPageService.selBusinessMyPageSales(orderType);
        return ResponseEntity.ok(result);
    }
}
