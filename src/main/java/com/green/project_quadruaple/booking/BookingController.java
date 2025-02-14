package com.green.project_quadruaple.booking;

import com.green.project_quadruaple.booking.model.BookingListGetReq;
import com.green.project_quadruaple.booking.model.BookingListResponse;
import com.green.project_quadruaple.booking.model.BookingPostReq;
import com.green.project_quadruaple.booking.model.BookingPostRes;
import com.green.project_quadruaple.booking.model.dto.ApproveData;
import com.green.project_quadruaple.common.model.ResponseWrapper;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

//@RestController
@Slf4j
@Controller
@RequestMapping("booking")
@RequiredArgsConstructor
@Tag(name = "예약")
public class BookingController {
    private final BookingService bookingService;

    @GetMapping
    @ResponseBody
    @Operation(summary = "예약 목록 불러오기")
    public ResponseWrapper<BookingListResponse> getBooking(BookingListGetReq req) {
        return bookingService.getBooking(req);
    }

    @PostMapping
    @ResponseBody
    @Operation(summary = "예약 등록")
    public ResponseWrapper<String> postBooking(@RequestBody BookingPostReq req) {
        return bookingService.postBooking(req);
    }

//    @PatchMapping
//    @Operation(summary = "예약 취소")
//    public ResponseWrapper<String> cancelBooking(@RequestBody ) {
//
//    }

    @Hidden
    @GetMapping("/pay-approve")
    public String getBookingList(@RequestParam("pg_token") String pgToken) {
        return "redirect:" + bookingService.approve(pgToken);
    }

//    @GetMapping("/complete")
//    @ResponseBody
//    public String getRedirect(@RequestParam("id") Long id) {
//        log.info("id = {}", id);
//        return String.valueOf(id);
//    }
}
