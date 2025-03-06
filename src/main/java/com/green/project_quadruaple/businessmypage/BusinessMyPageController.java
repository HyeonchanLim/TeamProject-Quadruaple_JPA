package com.green.project_quadruaple.businessmypage;

import com.green.project_quadruaple.businessmypage.model.BusinessMyPageBooking;
import com.green.project_quadruaple.businessmypage.model.BusinessMyPageBookingDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    @Operation(summary = "예약 현황")
    public ResponseEntity<?> getBusinessMyPageBooking() {
        List<BusinessMyPageBooking> result = businessMyPageService.selBusinessMyPageBooking();
        return ResponseEntity.ok(result);
    }

    @GetMapping("booking/details")
    @Operation(summary = "예약 상세 현황")
    public ResponseEntity<?> getBusinessMyPageBookingDetails(Long bookingId) {
        BusinessMyPageBookingDetails result = businessMyPageService.selBusinessMyPageBookingDetails(bookingId);
        return ResponseEntity.ok(result);
    }
}
