package com.green.project_quadruaple.coupon;

import com.green.project_quadruaple.common.config.security.AuthenticationFacade;
import com.green.project_quadruaple.coupon.model.CouponDto;
import com.green.project_quadruaple.coupon.model.CouponResponse;
import com.green.project_quadruaple.coupon.model.UsedExpiredCouponResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CouponService {
    private final CouponMapper couponMapper;
    private final AuthenticationFacade authenticationFacade;

    // 사용 가능한 쿠폰 조회
    public CouponResponse getCoupon() {
        long signedUserId = authenticationFacade.getSignedUserId();

        // 사용한 쿠폰 조회
        List<CouponDto> usedCoupons = couponMapper.selUsedCoupon(signedUserId);

        // 사용된 쿠폰의 title, expiredAt, distributeAt을 Set으로 저장
        Set<String> usedCouponsSet = usedCoupons.stream()
                .map(coupon -> coupon.getCouponId() + coupon.getTitle() + coupon.getExpiredAt().toString() + coupon.getDistributeAt().toString())  // 쿠폰 고유 정보를 하나로 묶어서 사용
                .collect(Collectors.toSet());

        // 해당 사용자의 모든 쿠폰 목록 조회
        List<CouponDto> allCoupons = couponMapper.findCouponsByUserId(signedUserId);

        // 현재 날짜와 시간 가져오기
        LocalDateTime now = LocalDateTime.now();

        // 만료되지 않았고 사용되지 않은 쿠폰만 필터링
        List<CouponDto> validCoupons = allCoupons.stream()
                .filter(coupon -> coupon.getExpiredAt().isAfter(now))  // 만료되지 않은 쿠폰
                .filter(coupon -> !usedCouponsSet.contains(coupon.getCouponId() + coupon.getTitle() + coupon.getExpiredAt().toString() + coupon.getDistributeAt().toString())) // 사용된 쿠폰 제외
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

        // 사용한 쿠폰 조회
        List<CouponDto> usedCoupons = couponMapper.selUsedCoupon(signedUserId);

        // 해당 사용자의 모든 쿠폰 목록 조회
        List<CouponDto> allCoupons = couponMapper.findCouponsByUserId(signedUserId);

        // 현재 날짜와 시간 가져오기
        LocalDateTime now = LocalDateTime.now();

        // 만료된 쿠폰만 필터링
        List<CouponDto> expiredCoupons = allCoupons.stream()
                .filter(coupon -> coupon.getExpiredAt().isBefore(now)) // 만료된 쿠폰만 선택
                .collect(Collectors.toList());

        // 사용한 쿠폰 중 만료된 쿠폰을 제외
        List<CouponDto> validExpiredCoupons = expiredCoupons.stream()
                .filter(expiredCoupon -> usedCoupons.stream()
                        .noneMatch(usedCoupon ->
                                usedCoupon.getTitle().equals(expiredCoupon.getTitle()) &&
                                        usedCoupon.getExpiredAt().equals(expiredCoupon.getExpiredAt()) &&
                                        usedCoupon.getDistributeAt().equals(expiredCoupon.getDistributeAt())))
                .collect(Collectors.toList());

        // UsedExpiredCouponResponse 생성 및 값 설정
        UsedExpiredCouponResponse usedExpiredCouponResponse = new UsedExpiredCouponResponse();
        usedExpiredCouponResponse.setUserId(signedUserId);
        usedExpiredCouponResponse.setUsedCouponCount(usedCoupons.size());  // 사용한 쿠폰 개수 추가
        usedExpiredCouponResponse.setExpiredCouponCount(validExpiredCoupons.size()); // 만료된 쿠폰 개수
        usedExpiredCouponResponse.setUsedCoupons(usedCoupons);
        usedExpiredCouponResponse.setExpiredCoupons(validExpiredCoupons);

        return usedExpiredCouponResponse;
    }
}
