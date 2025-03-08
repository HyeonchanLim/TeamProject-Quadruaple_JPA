package com.green.project_quadruaple.booking;

import com.green.project_quadruaple.booking.model.*;
import com.green.project_quadruaple.booking.model.dto.*;
import com.green.project_quadruaple.booking.repository.BookingMapper;
import com.green.project_quadruaple.booking.repository.BookingRepository;
import com.green.project_quadruaple.booking.repository.MenuRepository;
import com.green.project_quadruaple.booking.repository.RoomRepository;
import com.green.project_quadruaple.common.config.enumdata.ResponseCode;
import com.green.project_quadruaple.common.config.security.AuthenticationFacade;
import com.green.project_quadruaple.common.model.ResponseWrapper;
import com.green.project_quadruaple.entity.model.Booking;
import com.green.project_quadruaple.entity.model.Menu;
import com.green.project_quadruaple.entity.model.Room;
import com.green.project_quadruaple.entity.model.User;
import com.green.project_quadruaple.notice.NoticeService;
import com.green.project_quadruaple.point.PointHistoryRepository;
import com.green.project_quadruaple.user.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class BookingService {

    private final BookingMapper bookingMapper;
    private final String affiliateCode;
    private final String secretKey;
    private final String payUrl;
    private final BookingRepository bookingRepository;
    private final MenuRepository menuRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final NoticeService noticeService;
    private final PointHistoryRepository pointHistoryRepository;

    private KakaoReadyDto kakaoReadyDto;

    public BookingService(BookingMapper bookingMapper,
                          BookingRepository bookingRepository,
                          MenuRepository menuRepository,
                          RoomRepository roomRepository,
                          UserRepository userRepository,
                          NoticeService noticeService,
                          PointHistoryRepository pointHistoryRepository,
                          @Value("${kakao-api-const.affiliate-code}") String affiliateCode,
                          @Value("${kakao-api-const.secret-key}") String secretKey,
                          @Value("${kakao-api-const.url}") String payUrl, PointHistoryRepository pointViewRepository) {
        this.bookingMapper = bookingMapper;
        this.affiliateCode = affiliateCode;
        this.secretKey = secretKey;
        this.payUrl = payUrl;
        this.bookingRepository = bookingRepository;
        this.menuRepository = menuRepository;
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
        this.noticeService = noticeService;
        this.pointHistoryRepository = pointHistoryRepository;
    }

    // 예약 확정시 알람보내기
//    private void postConfirmBookingNotice (Booking booking, User user) {
//
//        StringBuilder content = new StringBuilder();
//
//        noticeService.postNotice(NoticeCategory.BOOKING,title,content.toString(), user, booking.getBookingId());
//    }
//
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

        int discount = 0;
        int price = menu.getPrice();

        if(couponId != null) { // 쿠폰이 요청에 담겨 있을 경우

            CouponDto couponDto = bookingMapper.selExistUserCoupon(signedUserId, couponId);
            if(couponDto == null || couponDto.getUsedCouponId() != null) { // 쿠폰 미소지시, 사용시 에러
                return new ResponseWrapper<>(ResponseCode.BAD_REQUEST.getCode(), "쿠폰 없음");
            }
            discount = price / couponDto.getDiscountRate();
            req.setReceiveId(couponDto.getReceiveId());
        }

        if(point != null) { // 포인트가 담겨있을 경우
            List<Integer> remainPoint = pointHistoryRepository.findRemainPointByUserId(signedUserId, PageRequest.of(0, 1));
            if(point > remainPoint.get(0)) {
                return new ResponseWrapper<>(ResponseCode.BAD_REQUEST.getCode(), "포인트 금액이 부족합니다.");
            }
            discount += point;
        }

        int resultPrice = price - discount;

        if(resultPrice != req.getActualPaid()) {
            return new ResponseWrapper<>(ResponseCode.BAD_REQUEST.getCode(), "금액이 맞지 않습니다.");
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
        String quantity = "1";
        String totalAmount = String.valueOf(req.getActualPaid());
        String taxFreeAmount = String.valueOf((req.getActualPaid()/10));

        params.put("cid", affiliateCode); // 가맹점 코드 - 테스트용
        params.put("partner_order_id", orderNo); // 주문 번호
        params.put("partner_user_id", String.valueOf(signedUserId)); // 회원 아이디
        params.put("item_name", "테스트 상품1"); // 상품 명
        params.put("quantity", quantity); // 상품 수량
        params.put("total_amount", totalAmount); // 상품 가격
        params.put("tax_free_amount", taxFreeAmount); // 상품 비과세 금액
        params.put("approval_url", "http://112.222.157.157:5231/api/booking/pay-approve"); // 성공시 url
        params.put("cancel_url", "http://112.222.157.157:5231/api/booking/kakaoPayCancle"); // 실패시 url
        params.put("fail_url", "http://112.222.157.157:5231/api/booking/kakaoPayFail");
//        params.put("approval_url", "http://localhost:8080/api/booking/pay-approve"); // 성공시 url
//        params.put("cancel_url", "http://localhost:8080/api/booking/kakaoPayCancle"); // 실패시 url
//        params.put("fail_url", "http://localhost:8080/api/booking/kakaoPayFail");

        HttpEntity<HashMap<String, String>> body = new HttpEntity<>(params, headers);

        try {
            kakaoReadyDto = restTemplate.postForObject(new URI(payUrl + "/online/v1/payment/ready"), body, KakaoReadyDto.class);
            log.info("kakaoDto = {}", kakaoReadyDto);
            if(kakaoReadyDto != null) {
                Booking booking = Booking.builder()
                        .menu(menu)
                        .room(room)
                        .user(signedUser)
                        .num(req.getNum())
                        .checkIn(checkInDate)
                        .checkOut(checkOutDate)
                        .totalPayment(req.getActualPaid())
                        .tid(kakaoReadyDto.getTid())
                        .state(0)
                        .build();
                kakaoReadyDto.setPartnerOrderId(orderNo);
                kakaoReadyDto.setPartnerUserId(String.valueOf(signedUserId));
                kakaoReadyDto.setBookingPostReq(req);
                kakaoReadyDto.setBooking(booking);

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

            if (bookingPostReq.getReceiveId() != null) {
                bookingMapper.insUsedCoupon(bookingPostReq.getReceiveId(), bookingPostReq.getBookingId());
            }

            Booking booking = kakaoReadyDto.getBooking();
            bookingRepository.save(booking);
            bookingRepository.flush();
            KakaoApproveDto approveDto = restTemplate.postForObject(new URI(payUrl + "/online/v1/payment/approve"), body, KakaoApproveDto.class);
            log.info("approveDto = {}", approveDto);
            if(approveDto == null) {
                throw new RuntimeException();
            }

            // 예약완료 알람발송
//            User noticeUser = userRepository.findById(bookingPostReq.getUserId()).orElse(null);
//            postConfirmBookingNotice(booking, noticeUser);

            int quantity = 1;

            BookingApproveInfoDto bookingApproveInfoDto = bookingMapper.selApproveBookingInfo(booking.getBookingId());
            String redirectParams = "?user_name=" + URLEncoder.encode(bookingApproveInfoDto.getUserName(), StandardCharsets.UTF_8) + "&"
                    + "title=" + URLEncoder.encode(bookingApproveInfoDto.getTitle(), StandardCharsets.UTF_8) + "&"
                    + "check_in=" + URLEncoder.encode(bookingApproveInfoDto.getCheckIn(), StandardCharsets.UTF_8) + "&"
                    + "check_out=" + URLEncoder.encode(bookingApproveInfoDto.getCheckOut(), StandardCharsets.UTF_8) + "&"
                    + "personnel=" + quantity;
            String url = "http://112.222.157.157:5231/booking/complete" + redirectParams;
//            String url = "http://localhost:8080/booking/complete" + redirectParams;
            return url;
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
}
