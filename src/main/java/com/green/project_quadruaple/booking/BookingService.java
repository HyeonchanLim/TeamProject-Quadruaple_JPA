package com.green.project_quadruaple.booking;

import com.green.project_quadruaple.booking.model.*;
import com.green.project_quadruaple.booking.model.dto.*;
import com.green.project_quadruaple.common.config.enumdata.ResponseCode;
import com.green.project_quadruaple.common.config.security.AuthenticationFacade;
import com.green.project_quadruaple.common.model.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class BookingService {

    private final BookingMapper bookingMapper;
    private final String affiliateCode;
    private final String secretKey;
    private final String payUrl;

    private KakaoReadyDto kakaoReadyDto;

    public BookingService(BookingMapper bookingMapper,
                          @Value("${kakao-api-const.affiliate-code}") String affiliateCode,
                          @Value("${kakao-api-const.secret-key}") String secretKey,
                          @Value("${kakao-api-const.url}") String payUrl) {
        this.bookingMapper = bookingMapper;
        this.affiliateCode = affiliateCode;
        this.secretKey = secretKey;
        this.payUrl = payUrl;
    }

    public ResponseWrapper<BookingListResponse> getBooking(BookingListGetReq req) {
        List<BookingListGetRes> bookingList = bookingMapper.getBooking(req);

        List<BookingListGetRes> beforeList = new ArrayList<>();
        List<BookingListGetRes> afterList = new ArrayList<>();

        for (BookingListGetRes booking : bookingList) {
            if ("progress".equals(booking.getBookingStatus())) {
                beforeList.add(booking);
            } else {
                afterList.add(booking);
            }
        }

        BookingListResponse res = new BookingListResponse();
        res.setBeforeList(beforeList);
        res.setAfterList(afterList);

        return new ResponseWrapper<>(ResponseCode.OK.getCode(), res);
    }

    // 예약 변경 -> 결제 , 취소 내역 업데이트 필요
    // check in - out 날짜 체크 (일정의 날짜 , check in - out 날짜 겹침x)
    // final_paymaent = 실제 결제 금액 맞는지 비교 필요
    // strf id 가 실제 상품과 맞는지 체크
    public ResponseWrapper<String> postBooking(BookingPostReq req) {
        Long signedUserId = AuthenticationFacade.getSignedUserId();
        Long couponId = req.getCouponId();
        List<MenuIdAndQuantityDto> orderList = req.getOrderList();

        if(!checkTime(req)) { // 체크인, 아웃 시간 예외 처리
            log.error("체크인, 아웃 시간 에러");
            return new ResponseWrapper<>(ResponseCode.BAD_REQUEST.getCode(), null);
        }

        if(couponId != null) { // 쿠폰이 요청에 담겨 있을 경우
            CouponDto couponDto = bookingMapper.selExistUserCoupon(signedUserId, couponId);
            if(couponDto == null || couponDto.getUsedCouponId() != null) { // 쿠폰 미소지시, 사용시 에러
                return new ResponseWrapper<>(ResponseCode.BAD_REQUEST.getCode(), "쿠폰 없음");
            }

            List<MenuDto> menuDtoList = bookingMapper.selMenu(orderList);
            for(MenuDto menuDto : menuDtoList) {
                for(MenuIdAndQuantityDto order : orderList) {
                    if(menuDto.getMenuId() == order.getMenuId()) { // 메뉴의 상품 가격이 일치하는지
                        if(menuDto.getStrfId() == req.getStrfId()) {
                            log.info("상품 가격 일치!");
                        } else {
                            log.info("상품 가격 불일치!");
                        }
                    }
                }
            }
            req.setReceiveId(couponDto.getReceiveId());
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime checkInDate = LocalDateTime.parse(req.getCheckIn(), formatter);
        LocalDateTime checkOutDate = LocalDateTime.parse(req.getCheckOut(), formatter);
        if(checkInDate.isAfter(checkOutDate) || checkInDate.isEqual(checkOutDate)) { // 날짜 체크
            throw new RuntimeException();
        }

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", secretKey);
        headers.add("Content-Type", "application/json");

        HashMap<String, String> params = new HashMap<>();

        LocalDateTime localDateTime = LocalDateTime.now();
        String orderNo = localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + (int)(Math.random()*1000);
        String quantity = String.valueOf(orderList.get(0).getQuantity());
        String totalAmount = String.valueOf(req.getActualPaid());
        String taxFreeAmount = String.valueOf((req.getActualPaid()/10));

        params.put("cid", affiliateCode); // 가맹점 코드 - 테스트용
        params.put("partner_order_id", orderNo); // 주문 번호
        params.put("partner_user_id", String.valueOf(signedUserId)); // 회원 아이디
        params.put("item_name", "테스트 상품1"); // 상품 명
        params.put("quantity", quantity); // 상품 수량
        params.put("total_amount", totalAmount); // 상품 가격
        params.put("tax_free_amount", taxFreeAmount); // 상품 비과세 금액
        params.put("approval_url", "http://112.222.157.156:5221/api/booking/pay-approve"); // 성공시 url
        params.put("cancel_url", "http://112.222.157.156:5221/api/booking/kakaoPayCancle"); // 실패시 url
        params.put("fail_url", "http://112.222.157.156:5221/api/booking/kakaoPayFail");
//        params.put("approval_url", "http://localhost:8080/api/booking/pay-approve"); // 성공시 url
//        params.put("cancel_url", "http://localhost:8080/api/booking/kakaoPayCancle"); // 실패시 url
//        params.put("fail_url", "http://localhost:8080/api/booking/kakaoPayFail");

        HttpEntity<HashMap<String, String>> body = new HttpEntity<>(params, headers);

        try {
            kakaoReadyDto = restTemplate.postForObject(new URI(payUrl + "/online/v1/payment/ready"), body, KakaoReadyDto.class);
            log.info("kakaoDto = {}", kakaoReadyDto);
            if(kakaoReadyDto != null) {
                kakaoReadyDto.setPartnerOrderId(orderNo);
                kakaoReadyDto.setPartnerUserId(String.valueOf(signedUserId));
                kakaoReadyDto.setBookingPostReq(req);
                req.getOrderList().get(0).setQuantity(Integer.parseInt(quantity));
                return new ResponseWrapper<>(ResponseCode.OK.getCode(), kakaoReadyDto.getNextRedirectPcUrl());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseWrapper<>(ResponseCode.OK.getCode(), null);
    }

    private boolean checkTime(BookingPostReq req) {
        stayOpenAndCloseAt stayOpenAndCloseAt = bookingMapper.selStrfOpenAndClose(req.getStrfId()); // 숙소 체크인, 체크아웃 시간

        DateTimeFormatter df1 = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime openCheck = LocalTime.parse(stayOpenAndCloseAt.getOpenCheck(), df1);
        LocalTime closeCheck = LocalTime.parse(stayOpenAndCloseAt.getCloseCheck(), df1);

        LocalTime checkIn = LocalTime.parse(req.getCheckIn().substring(11), df1);
        LocalTime checkOut = LocalTime.parse(req.getCheckOut().substring(11), df1);

        return (checkIn.isAfter(openCheck) || checkOut.isBefore(closeCheck));
    }

    public String approve(String pgToken) {

        String userId = kakaoReadyDto.getPartnerUserId();

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", secretKey);
        headers.add("Content-Type", "application/json");

        HashMap<String, String> params = new HashMap<>();

        params.put("cid", affiliateCode); // 가맹점 코드 - 테스트용
        params.put("tid", kakaoReadyDto.getTid()); // 결제 고유 번호, 준비단계 응답에서 가져옴
        params.put("partner_order_id", kakaoReadyDto.getPartnerOrderId()); // 주문 번호
        params.put("partner_user_id", String.valueOf(userId)); // 회원 아이디
        params.put("pg_token", pgToken); // 준비 단계에서 리다이렉트떄 받은 param 값

        HttpEntity<HashMap<String, String>> body = new HttpEntity<>(params, headers);

        try {
            BookingPostReq bookingPostReq = kakaoReadyDto.getBookingPostReq();
            bookingPostReq.setUserId(Long.parseLong(userId));
            bookingPostReq.setTid(kakaoReadyDto.getTid());
            bookingMapper.insBooking(bookingPostReq);
            if (bookingPostReq.getReceiveId() != null) {
                bookingMapper.insUsedCoupon(bookingPostReq.getReceiveId(), bookingPostReq.getBookingId());
            }

            KakaoApproveDto approveDto = restTemplate.postForObject(new URI(payUrl + "/online/v1/payment/approve"), body, KakaoApproveDto.class);
            log.info("approveDto = {}", approveDto);
            if(approveDto == null) {
                throw new RuntimeException();
            }

            int quantity = 0;

            List<MenuIdAndQuantityDto> orderList = bookingPostReq.getOrderList();
            if(orderList != null) {
                for (MenuIdAndQuantityDto menuIdAndQuantityDto : orderList) {
                    quantity += menuIdAndQuantityDto.getQuantity();
                }
            }

            BookingApproveInfoDto bookingApproveInfoDto = bookingMapper.selApproveBookingInfo(bookingPostReq.getBookingId());
            String redirectParams = "?user_name=" + URLEncoder.encode(bookingApproveInfoDto.getUserName(), StandardCharsets.UTF_8) + "&"
                    + "title=" + URLEncoder.encode(bookingApproveInfoDto.getTitle(), StandardCharsets.UTF_8) + "&"
                    + "check_in=" + URLEncoder.encode(bookingApproveInfoDto.getCheckIn(), StandardCharsets.UTF_8) + "&"
                    + "check_out=" + URLEncoder.encode(bookingApproveInfoDto.getCheckOut(), StandardCharsets.UTF_8) + "&"
                    + "personnel=" + quantity;
            String url = "http://112.222.157.156:5221/booking/complete" + redirectParams;
//            String url = "http://localhost:8080/booking/complete" + redirectParams;
            return url;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
