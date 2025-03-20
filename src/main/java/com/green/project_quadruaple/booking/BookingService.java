package com.green.project_quadruaple.booking;

import com.green.project_quadruaple.booking.model.*;
import com.green.project_quadruaple.booking.model.dto.*;
import com.green.project_quadruaple.booking.repository.BookingMapper;
import com.green.project_quadruaple.booking.repository.BookingRepository;
import com.green.project_quadruaple.booking.repository.MenuRepository;
import com.green.project_quadruaple.booking.repository.RoomRepository;
import com.green.project_quadruaple.common.config.constant.KakaopayConst;
import com.green.project_quadruaple.common.config.enumdata.ResponseCode;
import com.green.project_quadruaple.common.config.security.AuthenticationFacade;
import com.green.project_quadruaple.common.model.ResponseWrapper;
import com.green.project_quadruaple.coupon.repository.CouponRepository;
import com.green.project_quadruaple.coupon.repository.UsedCouponRepository;
import com.green.project_quadruaple.entity.base.NoticeCategory;
import com.green.project_quadruaple.entity.model.*;
import com.green.project_quadruaple.notice.NoticeService;
import com.green.project_quadruaple.point.PointHistoryRepository;
import com.green.project_quadruaple.user.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class BookingService {

    private final BookingMapper bookingMapper;
    private final BookingRepository bookingRepository;
    private final MenuRepository menuRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final NoticeService noticeService;
    private final PointHistoryRepository pointHistoryRepository;
    private final KakaopayConst kakaopayConst;
    private final CouponRepository couponRepository;
    private final UsedCouponRepository usedCouponRepository;

    private KakaoReadyDto kakaoReadyDto;

    public ResponseWrapper<List<BookingRes>> getBooking(Integer page) {

        long signedUserId = AuthenticationFacade.getSignedUserId();
        PageRequest pageAble = PageRequest.of(page, 30);

        List<BookingRes> bookingList = bookingRepository.findBookingListByUserId(signedUserId, pageAble);

        // 날짜 포맷팅
        for (BookingRes bookingRes : bookingList) {
            LocalDateTime createdAtLD = bookingRes.getCreatedAtLD();
            LocalDateTime checkInDateLD = bookingRes.getCheckInDateLD();
            LocalDateTime checkOutDateLD = bookingRes.getCheckOutDateLD();

            // 요일 SHORT ex) 월
            String dayOfWeekOfCreatedAt = createdAtLD.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREA);
            String dayOfWeekOfCheckIn = checkInDateLD.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREA);
            String dayOfWeekOfCheckOut = checkOutDateLD.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREA);

            // String 변환 ex) 2025-01-01 월
            String dateOfCreatedAt = createdAtLD.toLocalDate().toString() + " " + dayOfWeekOfCreatedAt;
            String dateOfCheckIn = checkInDateLD.toLocalDate().toString() + " " + dayOfWeekOfCheckIn;
            String dateOfCheckOut = checkOutDateLD.toLocalDate().toString() + " " + dayOfWeekOfCheckOut;

            bookingRes.setCreatedAt(dateOfCreatedAt);
            bookingRes.setCheckInDate(dateOfCheckIn);
            bookingRes.setCheckOutDate(dateOfCheckOut);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

            // 시간 세팅 ex) 14:00
            bookingRes.setCheckInTime(checkInDateLD.format(formatter));
            bookingRes.setCheckOutTime(checkOutDateLD.format(formatter));

        }

        return new ResponseWrapper<>(ResponseCode.OK.getCode(), bookingList);
    }

    // 예약 변경 -> 결제 , 취소 내역 업데이트 필요
    // check in - out 날짜 체크 (일정의 날짜 , check in - out 날짜 겹침x)
    // total_payment = 실제 결제 금액 맞는지 비교 필요
    // strfId 가 실제 상품과 맞는지 체크
    @Transactional
    public ResponseWrapper<String> postBooking(BookingPostReq req) {
        Long signedUserId = AuthenticationFacade.getSignedUserId();
        User signedUser = userRepository.findById(signedUserId).get();
        Long couponId = req.getCouponId();
        Integer point = req.getPoint();
        int actualPaid = req.getActualPaid();
        Room room = null;
        Menu menu = null;
        try { // room, menu null 체크, 값 바인딩
            room = roomRepository.findById(req.getRoomId()).get();
            menu = room.getMenu();
            if(menu == null) {
                throw new Exception();
            }
        } catch (Exception e) {
            return new ResponseWrapper<>(ResponseCode.BAD_REQUEST.getCode(), "존재하지 않는 메뉴");
        }

        if(!checkTime(req)) { // 체크인, 아웃 시간 예외 처리
            log.error("체크인, 아웃 시간 에러");
            return new ResponseWrapper<>(ResponseCode.BAD_REQUEST.getCode(), null);
        }

        int price = menu.getPrice();
        int resultPrice = price;
        if(couponId != null) { // 쿠폰이 요청에 담겨 있을 경우

            CouponDto couponDto = bookingMapper.selExistUserCoupon(signedUserId, couponId);
            if(couponDto == null || couponDto.getUsedCouponId() != null) { // 쿠폰 미소지시, 사용시 에러
                return new ResponseWrapper<>(ResponseCode.BAD_REQUEST.getCode(), "쿠폰 없음");
            }
            req.setReceiveId(couponDto.getReceiveId());
            resultPrice -= discountAmount(price, couponDto.getDiscountRate());
        }

         // 쿠폰 할인 적용

        Integer remainPoint = null;
        if(point != null) { // 포인트가 담겨있을 경우
            List<PointHistory> remainPoints = pointHistoryRepository.findPointHistoriesByUserId(signedUserId, PageRequest.of(0, 1));
            if(remainPoints != null && !remainPoints.isEmpty()) {
                remainPoint = remainPoints.get(0).getRemainPoint();
            }
            if(remainPoint == null || point > remainPoint) {
                return new ResponseWrapper<>(ResponseCode.BAD_REQUEST.getCode(), "포인트 금액이 부족합니다.");
            }

            if(point > resultPrice) {
                return new ResponseWrapper<>(ResponseCode.BAD_REQUEST.getCode(), "포인트 금액이 잘못되었습니다.");
            }
            remainPoint -= point;
            resultPrice = resultPrice - point; // 포인트 할인 적용
        }


        if(resultPrice != actualPaid) {
            return new ResponseWrapper<>(ResponseCode.BAD_REQUEST.getCode(), "금액이 맞지 않습니다.");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime checkInDate = LocalDateTime.parse(req.getCheckIn(), formatter);
        LocalDateTime checkOutDate = LocalDateTime.parse(req.getCheckOut(), formatter);
        if(checkInDate.isAfter(checkOutDate) || checkInDate.isEqual(checkOutDate)) { // 날짜 체크
            throw new RuntimeException();
        }



        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = setHeaders();

        HashMap<String, String> params = new HashMap<>();

        LocalDateTime localDateTime = LocalDateTime.now();
        String orderNo = localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + (int)(Math.random()*1000);
        String quantity = "1";
        String totalAmount = String.valueOf(actualPaid);
        String taxFreeAmount = getTaxFreeAmount(actualPaid);

        params.put("cid", kakaopayConst.getAffiliateCode()); // 가맹점 코드 - 테스트용
        params.put("partner_order_id", orderNo); // 주문 번호
        params.put("partner_user_id", String.valueOf(signedUserId)); // 회원 아이디
        params.put("item_name", "테스트 상품1"); // 상품 명
        params.put("quantity", quantity); // 상품 수량
        params.put("total_amount", totalAmount); // 상품 가격
        params.put("tax_free_amount", taxFreeAmount); // 상품 비과세 금액
        params.put("approval_url", kakaopayConst.getBookingApprovalUrl()); // 성공시 url
        params.put("cancel_url", kakaopayConst.getBookingCancelUrl()); // 실패시 url
        params.put("fail_url", kakaopayConst.getBookingFailUrl());


        HttpEntity<HashMap<String, String>> body = new HttpEntity<>(params, headers);

        try {
            kakaoReadyDto = restTemplate.postForObject(new URI(kakaopayConst.getUrl() + "/online/v1/payment/ready"), body, KakaoReadyDto.class);
            log.info("kakaoDto = {}", kakaoReadyDto);
            if(kakaoReadyDto != null) {
                Booking booking = Booking.builder()
                        .menu(menu)
                        .room(room)
                        .user(signedUser)
                        .num(req.getNum())
                        .usedPoint(point)
                        .checkIn(checkInDate)
                        .checkOut(checkOutDate)
                        .totalPayment(actualPaid)
                        .tid(kakaoReadyDto.getTid())
                        .state(0)
                        .build();
                kakaoReadyDto.setPartnerOrderId(orderNo);
                kakaoReadyDto.setPartnerUserId(String.valueOf(signedUserId));
                kakaoReadyDto.setBookingPostReq(req);
                kakaoReadyDto.setBooking(booking);
                kakaoReadyDto.setRemainPoint(remainPoint);

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
        LocalTime openCheck = LocalTime.parse(stayOpenAndCloseAt.getOpenCheckIn(), df1);
        LocalTime closeCheck = LocalTime.parse(stayOpenAndCloseAt.getCloseCheckOut(), df1);

        LocalTime checkIn = LocalTime.parse(req.getCheckIn().substring(11), df1);
        LocalTime checkOut = LocalTime.parse(req.getCheckOut().substring(11), df1);

        return (checkIn.isAfter(openCheck) || checkOut.isBefore(closeCheck));
    }

    @Transactional
    public String approve(String pgToken) {

        String userId = kakaoReadyDto.getPartnerUserId();
        String tid = kakaoReadyDto.getTid();

        log.info("approve pgToken = {}", pgToken);
        log.info("approve kakaoReadyDto = {}", kakaoReadyDto);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = setHeaders();

        HashMap<String, String> params = new HashMap<>();


        params.put("cid", kakaopayConst.getAffiliateCode()); // 가맹점 코드 - 테스트용
        params.put("tid", tid); // 결제 고유 번호, 준비단계 응답에서 가져옴
        params.put("partner_order_id", kakaoReadyDto.getPartnerOrderId()); // 주문 번호
        params.put("partner_user_id", String.valueOf(userId)); // 회원 아이디
        params.put("pg_token", pgToken); // 준비 단계에서 리다이렉트떄 받은 param 값

        for (String key : params.keySet()) {
            log.info("approve params = {}", params.get(key));
        }
        HttpEntity<HashMap<String, String>> body = new HttpEntity<>(params, headers);

        for (String key : body.getBody().keySet()) {
            log.info("approve body = {}", body.getBody().get(key));
        }

        try {
            BookingPostReq bookingPostReq = kakaoReadyDto.getBookingPostReq();
            bookingPostReq.setUserId(Long.parseLong(userId));
            bookingPostReq.setTid(tid);
            log.info("approve bookingPostReq = {}", bookingPostReq);
            Booking booking = kakaoReadyDto.getBooking();
            bookingRepository.save(booking);
            KakaoApproveDto approveDto = restTemplate.postForObject(new URI(kakaopayConst.getUrl() + "/online/v1/payment/approve"), body, KakaoApproveDto.class);
            log.info("approve approveDto = {}", approveDto);
            if(approveDto == null) {
                throw new RuntimeException();
            }

            int quantity = 1;

            bookingRepository.flush();
            if (bookingPostReq.getReceiveId() != null) {
                bookingMapper.insUsedCoupon(bookingPostReq.getReceiveId(), booking.getBookingId());
            }
            BookingApproveInfoDto bookingApproveInfoDto = bookingMapper.selApproveBookingInfo(booking.getBookingId());
            Integer remainPoint = kakaoReadyDto.getRemainPoint();
            if(remainPoint != null) {
                PointHistory pointHistory = PointHistory.builder()
                        .user(booking.getUser())
                        .category(0)
                        .relatedId(booking.getMenu().getStayTourRestaurFest().getStrfId())
                        .tid(tid)
                        .amount(booking.getUsedPoint())
                        .remainPoint(remainPoint)
                        .build();
                pointHistoryRepository.save(pointHistory);
            }

            //예약완료 알람발송
            User noticeUser = userRepository.findById(bookingPostReq.getUserId()).orElse(null);
            Integer usedPoint=booking.getUsedPoint()==null?0:booking.getUsedPoint();
            String title = booking.getCheckIn().toLocalDate().toString()+"에 입실하는 예약이 있습니다.";
            StringBuilder content = new StringBuilder(booking.getCheckIn().toLocalDate().toString()).append("부터 ")
                            .append(booking.getCheckOut().toLocalDate().toString()).append("까지 숙박하는 일정이 있습니다.")
                            .append("\n 숙박인원: ").append(booking.getNum())
                            .append("\n 총 결제 금액: ").append(booking.getTotalPayment())
                            .append("\n 사용한 포인트: ").append(usedPoint)
                            .append("\n 예상 체크인 시각: ").append(booking.getCheckIn().toLocalTime());
//            StayTourRestaurFest strf = booking.getMenu().getStayTourRestaurFest();
//            if(strf == null) {
//                booking=bookingRepository.findByBookingId(booking.getBookingId()).orElse(null);
//                strf=booking.getMenu().getStayTourRestaurFest();
//            }
//            StringBuilder title = new StringBuilder(booking.getCheckIn().toLocalDate().toString()).append(" ")
//                    .append(strf.getTitle()).append(" 예약 완료되었습니다.");
//            StringBuilder content = new StringBuilder(booking.getCheckIn().toLocalDate().toString()).append("부터 ")
//                            .append(booking.getCheckOut().toLocalDate().toString()).append("까지 숙박하는 ")
//                            .append(strf.getTitle()).append(" ").append(booking.getMenu().getTitle()).append("예약 완료되었습니다.")
//                            .append("\n 숙박인원: ").append(booking.getNum())
//                            .append("\n 총 결제 금액: ").append(booking.getTotalPayment())
//                            .append("\n 사용한 포인트: ").append(usedPoint)
//                            .append("\n 예상 체크인: ").append(booking.getCheckIn().toLocalTime())
//                            .append("\n 체크아웃시간: ").append(strf.getCloseCheckOut())
//                            .append("\n 문의 전화: ").append(strf.getTell());

            noticeService.postNotice(NoticeCategory.BOOKING,title.toString(),content.toString(),noticeUser, booking.getBookingId());

            log.info("approve content = {}", content);
            String redirectParams = "?user_name=" + URLEncoder.encode(bookingApproveInfoDto.getUserName(), StandardCharsets.UTF_8) + "&"
                    + "title=" + URLEncoder.encode(bookingApproveInfoDto.getTitle(), StandardCharsets.UTF_8) + "&"
                    + "check_in=" + URLEncoder.encode(bookingApproveInfoDto.getCheckIn(), StandardCharsets.UTF_8) + "&"
                    + "check_out=" + URLEncoder.encode(bookingApproveInfoDto.getCheckOut(), StandardCharsets.UTF_8) + "&"
                    + "personnel=" + quantity;

            log.info("approve redirectParams = {}", redirectParams);
            return kakaopayConst.getBookingCompleteUrl() + redirectParams;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /*
    * booking_id 받음
    * 로그인 유저가 해당 예약의 사용자인지 확인
    * */
    @Transactional
    public ResponseWrapper<String> refundBooking(BookingRefundReq req) {

        long signedUserId = AuthenticationFacade.getSignedUserId();
        Long bookingId = req.getBookingId();
        Booking booking = bookingRepository.findById(bookingId).get();
        String tid = booking.getTid();

        String message = null;
        if(booking.getUser().getUserId() != signedUserId) {
            message = "해당 예약의 사용자가 아닙니다.";
            return new ResponseWrapper<>(ResponseCode.Forbidden.getCode(), message);
        }

        LocalDate now = LocalDate.now();
        LocalDate checkIn = booking.getCheckIn().toLocalDate();
        if(now.isAfter(checkIn) || now.equals(checkIn)) {
            message = "환불 가능 기간이 아닙니다.";
            return new ResponseWrapper<>(ResponseCode.BAD_REQUEST.getCode(), message);
        }

        Integer usedPoint = booking.getUsedPoint();
        try {
            if(usedPoint != null && usedPoint > 0) { // 사용한 포인트가 있다면
                List<PointHistory> pointHistories = pointHistoryRepository.findPointHistoriesByUserId(booking.getUser().getUserId(), PageRequest.of(0, 1));
                if (pointHistories == null || pointHistories.isEmpty()) {
                    throw new RuntimeException();
                }
                PointHistory pointHistory = pointHistories.get(0);
                User busiUserId = bookingRepository.findBusiUserIdByBookingId(bookingId);
                PointHistory refundPointHistory = PointHistory.builder()
                        .user(busiUserId)
                        .category(2)
                        .relatedId(pointHistory.getPointHistoryId())
                        .tid(tid)
                        .amount(usedPoint)
                        .remainPoint(pointHistory.getRemainPoint() + usedPoint)
                        .build();
                pointHistoryRepository.save(refundPointHistory); // 포인트 되돌리기
            }

            List<UsedCoupon> usedCouponList = usedCouponRepository.findByBookingId(bookingId);
            usedCouponRepository.deleteAll(usedCouponList); // 사용 쿠폰 되돌리기

            booking.setState(3); // 예약 상태 변경

            int refundAmount = booking.getTotalPayment();
            if(now.isAfter(checkIn.minusDays(3))) {
                message = "50퍼센트 환불 완료";
                refundAmount = discountAmount(refundAmount, RefundRate.TWO_DAYS_AGO.getPercent()); // 50 퍼 환불
            } else if(now.isAfter(checkIn.minusDays(7))) {
                message = "70퍼센트 환불 완료";
                refundAmount = discountAmount(refundAmount, RefundRate.SIX_DAYS_AGO.getPercent()); // 70 퍼 환불
            } else {
                message = "100퍼센트 환불 완료";
            }

            String refundUrl = "/online/v1/payment/cancel";

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = setHeaders();

            HashMap<String, Object> params = new HashMap<>();

            params.put("cid", kakaopayConst.getAffiliateCode());
            params.put("tid", tid);
            params.put("cancel_amount", refundAmount);
            params.put("cancel_tax_free_amount", getTaxFreeAmount(booking.getTotalPayment()));

            HttpEntity<HashMap<String, Object>> body = new HttpEntity<>(params, headers);

            KakaoRefundDto refundDto = restTemplate.postForObject(kakaopayConst.getUrl() + refundUrl, body, KakaoRefundDto.class);
            return new ResponseWrapper<>(ResponseCode.OK.getCode(), message);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Transactional
    @Scheduled(cron = "0 0 6 * * ?")
    public void updateState() {
        bookingMapper.updateAllStateAfterCheckOut(LocalDateTime.now());
    }

    private HttpHeaders setHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", kakaopayConst.getSecretKey());
        headers.add("Content-Type", "application/json");
        return headers;
    }

    private String getTaxFreeAmount(int actualPaid) {
        return String.valueOf((actualPaid/10));
    }

    private int discountAmount(int menuAmount, int discountPer) {
        return (menuAmount / 100) * discountPer;
    }
}
