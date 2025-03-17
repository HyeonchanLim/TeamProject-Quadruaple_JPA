package com.green.project_quadruaple.notice;

import com.green.project_quadruaple.booking.repository.BookingRepository;
import com.green.project_quadruaple.common.config.enumdata.ResponseCode;
import com.green.project_quadruaple.common.config.jwt.JwtUser;
import com.green.project_quadruaple.common.config.jwt.UserRole;
import com.green.project_quadruaple.common.config.security.AuthenticationFacade;
import com.green.project_quadruaple.common.model.SizeConstants;
import com.green.project_quadruaple.common.model.ResponseWrapper;
import com.green.project_quadruaple.coupon.repository.CouponRepository;
import com.green.project_quadruaple.coupon.repository.ReceiveCouponRepository;
import com.green.project_quadruaple.entity.base.NoticeCategory;
import com.green.project_quadruaple.entity.model.*;
import com.green.project_quadruaple.notice.model.req.NoticeAdminSendReq;
import com.green.project_quadruaple.notice.model.res.NoticeLine;
import com.green.project_quadruaple.notice.model.res.NoticeLineRes;
import com.green.project_quadruaple.notice.model.res.NoticeOne;
import com.green.project_quadruaple.trip.TripRepository;
import com.green.project_quadruaple.trip.TripUserRepository;
import com.green.project_quadruaple.user.Repository.UserRepository;
import com.green.project_quadruaple.user.model.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
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
    private final RoleRepository roleRepository;

    // 타임아웃 시간 설정
    private static final long NOTICE_TIME_OUT = 3_600_000L; //1시간
    // 마지막 신규 알람 조회
    private LocalDateTime lastCheckedTime = LocalDateTime.now();
    // SSE 연결을 관리하는 저장소 (여러 유저 지원 가능)
    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

    public SseEmitter subscribe() {
        Long userId=authenticationFacade.getSignedUserId();
        SseEmitter emitter = new SseEmitter(NOTICE_TIME_OUT);
        log.info("subscribed userId: {}",userId);
        if(userId!=null){ emitters.put(userId, emitter);
            log.info("Stored in emitters: {}", emitters.containsKey(userId));}
        try {
            emitter.send(SseEmitter.event().name("INIT").data("연결 성공!"));
        } catch (IOException e) {
            log.warn("SSE 연결 중 오류 발생: {}", e.getMessage());
            emitter.complete();
        }

        emitter.onCompletion(() -> emitters.remove(userId));
        emitter.onTimeout(() -> emitters.remove(userId));

        return emitter;
    }

    @Scheduled(fixedDelay = 3000) // 3초마다 새 알림 확인
    @Transactional
    public void checkNewNotifications() {
        if(emitters.size()==0) { return; } //연결 상대가 없다면 실행하지 않음

        //신규 알림이 있는 유저 목록 불러오기
        List<Long> userIds = noticeRepository.getUsersWithNewNotices(lastCheckedTime);
        for (Long userId : userIds) {
            //알람 발송 메서드 호출
            sendNotification(userId);
            //발송 후 event table 정리
            noticeRepository.clearProcessedNotifications(userId);
        }
        lastCheckedTime = LocalDateTime.now();
    }

    //알람 발송
    public void sendNotification(Long userId) {
        SseEmitter emitter = emitters.get(userId); //연결 상대에 userId가 있는지 확인
        if (emitter != null) { //있다면 emitter send
            try {
                emitter.send(SseEmitter.event()
                            .name("exist unread notice")
                            .data(true));
            } catch (IOException e) {
                emitters.remove(userId);
            }
        }
    }




    //테스트 알람 추가
    public void testInsNotice(Long userId) {
        Notice notice = new Notice();
        notice.setContent("테스트 알람입니다.");
        notice.setTitle("테스트 제목입니다.");
        notice.setNoticeCategory(NoticeCategory.SERVICE);
        noticeRepository.save(notice);
        NoticeReceiveId noticeReceiveId = new NoticeReceiveId(userId, notice.getNoticeId());
        NoticeReceive noticeReceive = NoticeReceive.builder()
                .id(noticeReceiveId)
                .notice(notice)
                .user(userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found")))
                .opened(false)
                .build();
        noticeReceiveRepository.save(noticeReceive);
    }

    // 알람 리스트 확인
    public ResponseEntity<ResponseWrapper<NoticeLineRes>> noticeCheck(int startIdx) {
        if (!((SecurityContextHolder.getContext().getAuthentication().getPrincipal()) instanceof JwtUser)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ResponseWrapper<>(ResponseCode.Forbidden.getCode(), null));
        }
        long userId= authenticationFacade.getSignedUserId();
        List<NoticeLine> noticeLines = mapper.checkNotice(userId, startIdx, SizeConstants.getDefault_page_size()+1);
        if (noticeLines.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseWrapper<>(ResponseCode.NOT_FOUND.getCode(), null ));
        }
        boolean isMore = noticeLines.size() > SizeConstants.getDefault_page_size();
        if(isMore){  noticeLines.remove(noticeLines.size() - 1); }
        NoticeLineRes result=mapper.countNotice(userId);
        result.setNoticeLines(noticeLines);
        result.setIsMore(isMore);
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

    //관리자 알람 발송
    public void noticeAdmin(NoticeAdminSendReq p){
        long userId = authenticationFacade.getSignedUserId();

        // 사용자 권한 확인 (ADMIN 권한이 있는지 확인)
        List<Role> roles = roleRepository.findByUserUserId(userId);
        boolean isAdmin = roles.stream().anyMatch(role -> role.getRole() == UserRole.ADMIN);
        if (!isAdmin) {
            log.error("권한이 없습니다. 사용자 권한: {}", roles.isEmpty() ? "없음" : roles.get(0).getRole());
        }

        Notice notice = Notice.builder()
                .noticeCategory(NoticeCategory.SERVICE)
                .title(p.getTitle())
                .content(p.getContent())
                .build();
        noticeRepository.saveAndFlush(notice);

        long noticeId=notice.getNoticeId();

        List<User> users =p.getRole()!=null?userRepository.findByRole(p.getRole()):userRepository.findNotInRole(UserRole.ADMIN);
        for (User u : users) {
            noticeReceiveRepository.save(
                NoticeReceive.builder()
                        .id(new NoticeReceiveId(u.getUserId(), noticeId))
                        .notice(notice)
                        .user(u)
                        .opened(false)
                        .build()
            );
        }
        noticeReceiveRepository.flush();
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
                noticeReceiveRepository.save(NoticeReceive.builder()
                        .id(new NoticeReceiveId(user.getUserId(), notice.getNoticeId()))
                        .user(user)
                        .notice(notice)
                        .opened(false)
                        .build());
            }
        }
        noticeReceiveRepository.flush();
    }

    //쿠폰이 만료되기 사흘 전
    public void dDayExpireCouponNotice() {
        List<Coupon> coupons = couponRepository.findExpireBeforeCoupon();
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
                noticeReceiveRepository.save(NoticeReceive.builder()
                        .id(new NoticeReceiveId(user.getUserId(), notice.getNoticeId()))
                        .user(user)
                        .notice(notice)
                        .opened(false)
                        .build());
            }
        }
        noticeReceiveRepository.flush();
    }

    //예약된 숙소 하루 전
    public void dDayBookingNotice() {
        List<Booking> bookings = bookingRepository.findBookingBeforeExpired();
        for(Booking booking : bookings) {
            Menu menu = booking.getMenu();
            StayTourRestaurFest strf=menu.getStayTourRestaurFest();
            String title=strf.getTitle()+" 숙박일이 내일 입니다.";
            StringBuilder content = new StringBuilder();
            content.append("익일").append(booking.getCreatedAt().toLocalDate()).append("에 예약한 ")
                    .append(strf.getTitle()).append(" 숙박 예정이 있습니다.")
                    .append(" \n")
                    .append("숙박 장소 : ").append(strf.getAddress())
                    .append(" \n")
                    .append("tel : ").append(strf.getTell())
                    .append(" \n")
                    .append("입실 예정 시각 : ").append(booking.getCheckIn().toLocalTime().format(DateTimeFormatter.ofPattern("HH시 mm분")))
                    .append(" \n")
                    .append("퇴실 시각 : ").append(strf.getCloseCheckOut().format(DateTimeFormatter.ofPattern("HH시 mm분")))
                    .append(" \n")
                    .append("예약 객실 : ").append(menu.getTitle());
            Notice notice=Notice.builder()
                    .noticeCategory(NoticeCategory.BOOKING)
                    .foreignNum(booking.getBookingId())
                    .title(title)
                    .content(content.toString())
                    .build();
            noticeRepository.saveAndFlush(notice);

            noticeReceiveRepository.save(NoticeReceive.builder()
                    .id(new NoticeReceiveId(booking.getUser().getUserId(), notice.getNoticeId()))
                    .user(booking.getUser())
                    .notice(notice)
                    .opened(false)
                    .build());
        }
        noticeRepository.flush();
    }

}
