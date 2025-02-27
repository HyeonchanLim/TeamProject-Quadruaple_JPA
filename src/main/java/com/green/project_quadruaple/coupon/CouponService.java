package com.green.project_quadruaple.coupon;

import com.green.project_quadruaple.common.config.security.AuthenticationFacade;
import com.green.project_quadruaple.coupon.model.*;
import com.green.project_quadruaple.coupon.repository.CouponRepository;
import com.green.project_quadruaple.coupon.repository.ReceiveCouponRepository;
import com.green.project_quadruaple.coupon.repository.UsedCouponRepository;
import com.green.project_quadruaple.entity.model.Coupon;
import com.green.project_quadruaple.entity.model.UsedCoupon;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CouponService {
    private final CouponMapper couponMapper;
    private final AuthenticationFacade authenticationFacade;
    private final CouponRepository couponRepository;
    private final ReceiveCouponRepository receiveCouponRepository;
    private final UsedCouponRepository usedCouponRepository;

    // 사용 가능한 쿠폰 조회
    public CouponResponse getCoupon() {
        long signedUserId = authenticationFacade.getSignedUserId();

        // 사용한 쿠폰 조회
        List<UsedCoupon> usedCoupons = usedCouponRepository.findUsedCouponByUserId(signedUserId);

        // 사용된 쿠폰의 title, expiredAt, distributeAt을 Set으로 저장
        Set<String> usedCouponsSet = usedCoupons.stream()
                .map(u -> u.getReceiveCoupon().getCoupon().getCouponId() + u.getReceiveCoupon().getCoupon().getTitle() +
                        u.getReceiveCoupon().getCoupon().getExpiredAt().toString() + u.getReceiveCoupon().getCoupon().getDistributeAt().toString())  // 쿠폰 고유 정보를 하나로 묶어서 사용
                .collect(Collectors.toSet());

        // 해당 사용자의 모든 쿠폰 목록 조회
        List<Coupon> allCoupons = couponRepository.findCouponsByUserId(signedUserId);

        // 현재 날짜와 시간 가져오기
        LocalDate now = LocalDate.now();

        // 만료되지 않았고 사용되지 않은 쿠폰만 필터링
        List<CouponDto> validCoupons = allCoupons.stream()
                .filter(coupon -> coupon.getExpiredAt().isAfter(LocalDateTime.now()))
                .filter(coupon -> !usedCouponsSet.contains(coupon.getCouponId() + coupon.getTitle() + coupon.getExpiredAt().toString() + coupon.getDistributeAt().toString()))
                .map(coupon -> {
                    CouponDto couponDto = new CouponDto();
                    couponDto.setCouponId(coupon.getCouponId().toString());
                    couponDto.setTitle(coupon.getTitle());
                    couponDto.setExpiredAt(coupon.getExpiredAt());
                    couponDto.setDistributeAt(coupon.getDistributeAt());
                    couponDto.setDaysLeft(ChronoUnit.DAYS.between(now, coupon.getExpiredAt().toLocalDate())); // D-Day 설정
                    return couponDto;
                })
                .collect(Collectors.toList());

        // CouponResponse 생성 및 값 설정
        CouponResponse couponResponse = new CouponResponse();
        couponResponse.setUserId(signedUserId);
        couponResponse.setAvailableCouponCount(validCoupons.size());
        couponResponse.setCoupons(validCoupons);

        return couponResponse;
    }

    // 만료된 쿠폰, 사용한 쿠폰 조회
    public UsedExpiredCouponResponse getUsedExpiredCoupon() {
        long signedUserId = authenticationFacade.getSignedUserId();
        LocalDateTime now = LocalDateTime.now();

        // 사용한 쿠폰 조회
        List<UsedCoupon> usedCoupons = usedCouponRepository.findByReceiveCoupon_User_UserId(signedUserId);

        // 만료된 쿠폰 조회
        List<Coupon> expiredCoupons = couponRepository.findExpiredCouponsByUserId(signedUserId, now);

        // UsedCoupon 엔티티 → DTO 변환
        List<UsedCouponDto> usedCouponDtos = usedCoupons.stream()
                .map(uc -> new UsedCouponDto(
                        uc.getReceiveId(),
                        uc.getReceiveCoupon().getCoupon().getCouponId(),
                        uc.getReceiveCoupon().getCoupon().getTitle(),
                        uc.getReceiveCoupon().getCoupon().getExpiredAt(),
                        uc.getReceiveCoupon().getCoupon().getDiscountPer()
                ))
                .collect(Collectors.toList());

        // 사용한 쿠폰 ID Set으로 변환 (중복 제거)
        Set<Long> usedCouponIds = usedCoupons.stream()
                .map(uc -> uc.getReceiveCoupon().getCoupon().getCouponId())
                .collect(Collectors.toSet());

        // ExpiredCoupon 엔티티 → DTO 변환 (사용한 쿠폰 제외)
        List<ExpiredCouponDto> validExpiredCoupons = expiredCoupons.stream()
                .filter(coupon -> !usedCouponIds.contains(coupon.getCouponId()))
                .map(coupon -> new ExpiredCouponDto(
                        coupon.getCouponId(),
                        coupon.getTitle(),
                        coupon.getExpiredAt(),
                        coupon.getDiscountPer()
                ))
                .collect(Collectors.toList());

        // UsedExpiredCouponResponse 생성 및 값 설정
        UsedExpiredCouponResponse response = new UsedExpiredCouponResponse();
        response.setUserId(signedUserId);
        response.setUsedCouponCount(usedCouponDtos.size());
        response.setExpiredCouponCount(validExpiredCoupons.size());
        response.setUsedCoupons(usedCouponDtos);
        response.setExpiredCoupons(validExpiredCoupons);

        return response;
    }
}
