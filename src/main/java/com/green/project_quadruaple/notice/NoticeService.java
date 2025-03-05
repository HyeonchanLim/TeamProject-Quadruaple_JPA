package com.green.project_quadruaple.notice;

import com.green.project_quadruaple.booking.repository.BookingRepository;
import com.green.project_quadruaple.booking.repository.MenuRepository;
import com.green.project_quadruaple.common.config.enumdata.ResponseCode;
import com.green.project_quadruaple.common.config.jwt.JwtUser;
import com.green.project_quadruaple.common.config.security.AuthenticationFacade;
import com.green.project_quadruaple.common.model.SizeConstants;
import com.green.project_quadruaple.common.model.ResponseWrapper;
import com.green.project_quadruaple.coupon.repository.CouponRepository;
import com.green.project_quadruaple.coupon.repository.ReceiveCouponRepository;
import com.green.project_quadruaple.entity.base.NoticeCategory;
import com.green.project_quadruaple.entity.model.*;
import com.green.project_quadruaple.notice.model.res.NoticeLine;
import com.green.project_quadruaple.notice.model.res.NoticeOne;
import com.green.project_quadruaple.strf.StrfRepository;
import com.green.project_quadruaple.trip.TripRepository;
import com.green.project_quadruaple.trip.TripUserRepository;
import com.green.project_quadruaple.user.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeReceiveRepository noticeReceiveRepository;
    private final NoticeRepository noticeRepository;
    private final AuthenticationFacade authenticationFacade;
    private final UserRepository userRepository;
    private final NoticeMapper mapper;
    private final TripRepository tripRepository;
    private final BookingRepository bookingRepository;
    private final CouponRepository couponRepository;
    private final ReceiveCouponRepository receiveCouponRepository;
    private final TripUserRepository tripUserRepository;
    private final StrfRepository strfRepository;
    private final MenuRepository menuRepository;

    // SSE 연결을 관리하는 저장소 (여러 유저 지원 가능)
    private final ConcurrentHashMap<Long, CopyOnWriteArrayList<SseEmitter>> emitters = new ConcurrentHashMap<>();

    //미열람 알람 개수를 실시간으로 전송하는 SSE
    public SseEmitter noticeCnt() {
        long userId = 116L;
        //long userId = authenticationFacade.getSignedUserId();
        SseEmitter sseEmitter = new SseEmitter(30 * 1000L); // 60초 타임아웃 설정
        // 유저별 SseEmitter 리스트 저장
        emitters.computeIfAbsent(userId, k -> new CopyOnWriteArrayList<>()).add(sseEmitter);
        // 연결 종료 시 리스트에서 제거
        sseEmitter.onCompletion(() -> removeEmitter(userId, sseEmitter));
        sseEmitter.onTimeout(() -> removeEmitter(userId, sseEmitter));
        // 현재 미열람 알람 개수를 즉시 전송
        sendNoticeCount(userId);
        return sseEmitter;
    }

    // 30초마다 알림 갯수 전송
    public void sendNoticeCount(long userId) {
        boolean noticeCnt = noticeReceiveRepository.existsUnreadNoticesByUserId(userId);
        if (emitters.containsKey(userId)) {
            for (SseEmitter emitter : emitters.get(userId)) {
                try {
                    emitter.send(SseEmitter.event()
                            .name("notice-count")
                            .data(noticeCnt));
                } catch (IOException e) {
                    removeEmitter(userId, emitter);
                }
            }
        }
    }

    //SSE 연결 제거
    private void removeEmitter(long userId, SseEmitter emitter) {
        if (emitters.containsKey(userId)) {
            emitters.get(userId).remove(emitter);
        }
    }

    //테스트 알람 추가
    public void testInsNotice() {
        Notice notice = new Notice();
        notice.setContent("테스트 알람입니다.");
        notice.setTitle("테스트 제목입니다.");
        notice.setNoticeCategory(NoticeCategory.SERVICE);
        noticeRepository.save(notice);
        NoticeReceiveId noticeReceiveId = new NoticeReceiveId(116L, notice.getNoticeId());
        NoticeReceive noticeReceive = NoticeReceive.builder()
                .id(noticeReceiveId)
                .notice(notice)
                .user(userRepository.findById(116L).orElseThrow(() -> new RuntimeException("User not found")))
                .opened(false)
                .build();
        noticeReceiveRepository.save(noticeReceive);
    }

    // 알람 리스트 확인
    public ResponseEntity<ResponseWrapper<List<NoticeLine>>> noticeCheck(int startIdx) {
        if (!((SecurityContextHolder.getContext().getAuthentication().getPrincipal()) instanceof JwtUser)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ResponseWrapper<>(ResponseCode.Forbidden.getCode(), null));
        }
        List<NoticeLine> result = mapper.checkNotice(authenticationFacade.getSignedUserId(), startIdx, SizeConstants.getDefault_page_size());
        if (result.size() == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseWrapper<>(ResponseCode.NOT_FOUND.getCode(), new ArrayList<>()));
        }
        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), result));
    }

    // 알람 하나 확인
    public ResponseEntity<ResponseWrapper<NoticeOne>> checkNoticeOne(long noticeId) {
        if (!((SecurityContextHolder.getContext().getAuthentication().getPrincipal()) instanceof JwtUser)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ResponseWrapper<>(ResponseCode.Forbidden.getCode(), null));
        }
        Notice no = noticeRepository.findById(noticeId).orElse(null);
        NoticeReceive nr = noticeReceiveRepository.findById(new NoticeReceiveId(authenticationFacade.getSignedUserId(), noticeId)).orElse(null);
        if (no == null || nr == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseWrapper<>(ResponseCode.NOT_FOUND.getCode(), null));
        }
        nr.setOpened(true);
        noticeReceiveRepository.save(nr);
        NoticeOne result = new NoticeOne(no, nr.getCreatedAt());
        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), result));
    }

    // 알람 전체 확인
    @Transactional
    public ResponseEntity<ResponseWrapper<Integer>> readAllNotice() {
        if (!((SecurityContextHolder.getContext().getAuthentication().getPrincipal()) instanceof JwtUser)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ResponseWrapper<>(ResponseCode.Forbidden.getCode(), null));
        }
        int result = noticeReceiveRepository.markNoticeAsRead(authenticationFacade.getSignedUserId());
        if (result == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseWrapper<>(ResponseCode.NOT_FOUND.getCode(), null));
        }
        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), result));
    }

    //단일 알람 발송 기본 템플릿
    public void postNotice(NoticeCategory category, String title, String content, User user, Long foreignNum) {
        Notice notice = new Notice();
        notice.setNoticeCategory(category);
        notice.setContent(content);
        notice.setTitle(title);
        notice.setForeignNum(foreignNum);
        noticeRepository.saveAndFlush(notice);
        NoticeReceive noticeReceive = NoticeReceive.builder()
                .id(new NoticeReceiveId(user.getUserId(), notice.getNoticeId()))
                .notice(notice)
                .user(user)
                .opened(false)
                .build();
        noticeReceiveRepository.save(noticeReceive);
    }

    //자동 시간계산 알람 발송
    @Scheduled(cron = "0 0 5 * * *")
    public void autoSendNotice() {
        dDayTripNotice();
        dDayExpireCouponNotice();
        dDayBookingNotice();
    }

    //일주일 앞으로 다가온 여행
    public void dDayTripNotice() {
        List<Trip> trips = tripRepository.findTripBefore7days();
        List<NoticeReceive> noticeReceives = new ArrayList<>();
        for (Trip trip : trips) {
            String title = String.format("%s 여행이 일주일 앞으로 다가왔습니다!", trip.getTitle());
            List<User> users = tripUserRepository.findUserByTrip(trip);
            StringBuilder content = new StringBuilder();
            content.append(trip.getPeriod().getStartAt()).append("부터 ")
                    .append(trip.getPeriod().getEndAt()).append("까지 ");
            if (users.size() > 1) {
                for (int i = 0; i < users.size(); i++) {
                    if (i != users.size() - 1) {
                        content.append(users.get(i).getName()).append("님, ");
                    } else {
                        content.append(users.get(i).getName()).append("님과 ");
                    }
                }
            }
            content.append("떠나는 ").append(title).append("준비는 다 되셨나요? 출발이 머지 않았습니다!");
            Notice notice = Notice.builder()
                    .noticeCategory(NoticeCategory.TRIP)
                    .title(title)
                    .content(content.toString())
                    .foreignNum(trip.getTripId())
                    .build();
            noticeRepository.saveAndFlush(notice);

            for(User user : users) {
                NoticeReceive noticeReceive=NoticeReceive.builder()
                        .id(new NoticeReceiveId(user.getUserId(), notice.getNoticeId()))
                        .user(user)
                        .notice(notice)
                        .opened(false)
                        .build();
                noticeReceives.add(noticeReceive);
            }
        }
        noticeReceiveRepository.saveAll(noticeReceives);
    }

    //쿠폰이 만료되기 사흘 전
    public void dDayExpireCouponNotice() {
        List<Coupon> coupons = couponRepository.findExpireBeforeCoupon();
        List<NoticeReceive> noticeReceives = new ArrayList<>();
        for(Coupon coupon : coupons) {
            StringBuilder content = new StringBuilder();
            String title = coupon.getTitle()+"이 곧 만료됩니다.";
            content.append(coupon.getDiscountPer()).append("% 할인되는 ")
                    .append(coupon.getTitle()).append("이 ")
                    .append(coupon.getExpiredAt())
                    .append("에 만료됩니다. 기간 안에 사용하여 더 저렴한 혜택을 놓치지 마세요!");
            Notice notice=Notice.builder()
                    .foreignNum(coupon.getCouponId())
                    .noticeCategory(NoticeCategory.COUPON)
                    .title(title)
                    .content(content.toString())
                    .build();
            noticeRepository.saveAndFlush(notice);

            List<User> users=receiveCouponRepository.findByCoupon(coupon)
                    .stream().map(ReceiveCoupon::getUser).collect(Collectors.toList());
            for(User user : users) {
                NoticeReceive noticeReceive=NoticeReceive.builder()
                        .id(new NoticeReceiveId(user.getUserId(), notice.getNoticeId()))
                        .user(user)
                        .notice(notice)
                        .opened(false)
                        .build();
                noticeReceives.add(noticeReceive);
            }
        }
        noticeReceiveRepository.saveAll(noticeReceives);
    }

    //예약된 숙소 하루 전
    public void dDayBookingNotice() {
        List<Booking> bookings = bookingRepository.findBookingBeforeExpired();
        List<NoticeReceive> noticeReceives = new ArrayList<>();
        for(Booking booking : bookings) {
            Menu menu = booking.getMenu();
            StayTourRestaurFest strf=menu.getStayTourRestaurFest();
            String title=strf.getTitle()+" 숙박일이 내일 입니다.";
            StringBuilder content = new StringBuilder();
            content.append("익일").append(booking.getCreatedAt().toLocalDate()).append("에 예약한 ")
                    .append(strf.getTitle()).append(" 숙박 예정이 있습니다.")
                    .append("\n")
                    .append("숙박 장소 : ").append(strf.getAddress())
                    .append("\n")
                    .append("tel : ").append(strf.getTell())
                    .append("\n")
                    .append("입실 예정 시각 : ").append(booking.getCheckIn().toLocalTime().format(DateTimeFormatter.ofPattern("HH시 mm분")))
                    .append("\n")
                    .append("퇴실 시각 : ").append(strf.getCloseCheckOut().format(DateTimeFormatter.ofPattern("HH시 mm분")))
                    .append("\n")
                    .append("예약 객실 : ").append(menu.getTitle());
            Notice notice=Notice.builder()
                    .noticeCategory(NoticeCategory.BOOKING)
                    .foreignNum(booking.getBookingId())
                    .title(title)
                    .content(content.toString())
                    .build();
            noticeRepository.saveAndFlush(notice);

            NoticeReceive noticeReceive=NoticeReceive.builder()
                    .id(new NoticeReceiveId(booking.getUser().getUserId(), notice.getNoticeId()))
                    .user(booking.getUser())
                    .notice(notice)
                    .opened(false)
                    .build();
            noticeReceives.add(noticeReceive);
        }
        noticeReceiveRepository.saveAll(noticeReceives);
    }

}
