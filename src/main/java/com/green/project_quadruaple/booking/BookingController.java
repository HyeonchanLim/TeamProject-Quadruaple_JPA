package com.green.project_quadruaple.booking;

import com.green.project_quadruaple.booking.model.BookingPostReq;
import com.green.project_quadruaple.booking.model.dto.BookingRes;
import com.green.project_quadruaple.booking.repository.BookingMapper;
import com.green.project_quadruaple.common.model.ResponseWrapper;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController
@Slf4j
@Controller
@RequestMapping("booking")
@RequiredArgsConstructor
@Tag(name = "예약")
public class BookingController {
    private final BookingService bookingService;
    private final BookingMapper bookingMapper;

    @GetMapping
    @ResponseBody
    @Operation(summary = "예약 목록 불러오기")
    public ResponseWrapper<List<BookingRes>> getBooking(Integer page) {
        return bookingService.getBooking(page);
    }

    @PostMapping
    @ResponseBody
    @Operation(summary = "예약 등록")
    public ResponseWrapper<String> postBooking(@RequestBody BookingPostReq req) {
        return bookingService.postBooking(req);
    }

//    @PatchMapping
//    @Operation(summary = "예약 취소", description = "예약 취소를 누르면 사업자에게 예약 취소 접수됨.")
//    public ResponseWrapper<String> cancelBooking(@RequestBody ) {
//
//    }

    @Hidden
    @GetMapping("/pay-approve")
    public String approve(@RequestParam("pg_token") String pgToken) {
        return "redirect:" + bookingService.approve(pgToken);
    }
}
