package com.green.project_quadruaple.coupon.admin;

import com.green.project_quadruaple.common.config.jwt.UserRole;
import com.green.project_quadruaple.common.config.security.AuthenticationFacade;
import com.green.project_quadruaple.coupon.model.CouponAdminGetDto;
import com.green.project_quadruaple.coupon.model.CouponAdminPostReq;
import com.green.project_quadruaple.coupon.model.CouponAdminUpdateDto;
import com.green.project_quadruaple.coupon.model.CouponBusinessUpdateDto;
import com.green.project_quadruaple.coupon.repository.CouponRepository;
import com.green.project_quadruaple.entity.model.Coupon;
import com.green.project_quadruaple.entity.model.Role;
import com.green.project_quadruaple.entity.model.StayTourRestaurFest;
import com.green.project_quadruaple.strf.StrfRepository;
import com.green.project_quadruaple.user.model.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CouponAdminIssuanceService {
    private final RoleRepository roleRepository;
    private final CouponRepository couponRepository;
    private final StrfRepository strfRepository;
    private final AuthenticationFacade authenticationFacade;

    public int insCoupon(CouponAdminPostReq req) {
        long userId = authenticationFacade.getSignedUserId();

        // 사용자 권한 확인 (ADMIN 권한이 있는지 확인)
        List<Role> roles = roleRepository.findByUserUserId(userId);
        boolean isAdmin = roles.stream().anyMatch(role -> role.getRole() == UserRole.ADMIN);

        if (!isAdmin) {
            log.error("쿠폰 발급 권한이 없습니다. 사용자 권한: {}", roles.isEmpty() ? "없음" : roles.get(0).getRole());
            return 0;  // 권한이 없으면 쿠폰 발급하지 않음
        }

        // 쿠폰 객체 생성
        Coupon coupon = new Coupon();
        coupon.setTitle(req.getTitle());
        coupon.setExpiredAt(req.getExpiredAt());
        coupon.setDiscountPer(req.getDiscountPer());
        coupon.setDistributeAt(req.getDistributeAt());

        // 쿠폰 발급
        couponRepository.save(coupon);

        log.info("쿠폰 발급 성공: {}", coupon.getTitle());
        return 1;
    }

    @Transactional
    public List<CouponAdminGetDto> getCouponsByUser() {
        long userId = authenticationFacade.getSignedUserId();

        // 로그인한 유저가 ADMIN 권한인지 확인
        List<Role> roles = roleRepository.findByUserUserId(userId);
        boolean isAdmin = roles.stream().anyMatch(role -> role.getRole() == UserRole.ADMIN);

        if (!isAdmin) {
            log.warn("유저 {}는 Admin이 아니므로 쿠폰을 조회할 수 없습니다.", userId);
            return new ArrayList<>(); // 권한이 없으면 빈 리스트 반환
        }

        // 모든 쿠폰 조회
        List<Coupon> coupons = couponRepository.findAll();  // 모든 쿠폰을 가져오기

        // 로그인한 유저가 발급한 쿠폰만 필터링
        List<CouponAdminGetDto> userCoupons = coupons.stream()
                .filter(coupon -> coupon.getStrf() == null)
                .map(CouponAdminGetDto::new)
                .collect(Collectors.toList());

        if (userCoupons.isEmpty()) {
            log.warn("유저 {}가 발급한 쿠폰이 없습니다.", userId);
            return new ArrayList<>();
        }

        return userCoupons;
    }

    @Transactional
    public int updCoupon(CouponAdminUpdateDto req) {
        long userId = authenticationFacade.getSignedUserId();

        // 로그인한 유저가 ADMIN 권한인지 확인
        List<Role> roles = roleRepository.findByUserUserId(userId);
        boolean isAdmin = roles.stream().anyMatch(role -> role.getRole() == UserRole.ADMIN);

        if (!isAdmin) {
            log.warn("유저 {}는 Admin이 아니므로 쿠폰을 조회할 수 없습니다.", userId);
            return 0; // 권한이 없으면 0 반환
        }

        // 쿠폰 조회 (존재하지 않으면 예외 발생)
        Coupon coupon = couponRepository.findById(req.getCouponId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 쿠폰입니다."));

        // strf가 null인지 체크 (null인 경우에만 수정 가능)
        if (coupon.getStrf() != null) {
            log.error("쿠폰 수정 불가: strf가 존재합니다. 쿠폰 ID: {}", req.getCouponId());
            return 0;
        }

        // 쿠폰 정보 업데이트
        coupon.setTitle(req.getTitle());
        coupon.setExpiredAt(req.getExpiredAt());
        coupon.setDiscountPer(req.getDiscountPer());
        coupon.setDistributeAt(req.getDistributeAt());

        // 변경 사항 저장
        couponRepository.save(coupon);

        log.info("쿠폰 수정 성공: {}", coupon.getTitle());
        return 1;
    }

    public int countAdminCoupon() {
        long userId = authenticationFacade.getSignedUserId();

        // 로그인한 유저가 ADMIN 권한인지 확인
        List<Role> roles = roleRepository.findByUserUserId(userId);
        boolean isAdmin = roles.stream().anyMatch(role -> role.getRole() == UserRole.ADMIN);

        if (!isAdmin) {
            log.warn("유저 {}는 Admin이 아니므로 쿠폰을 조회할 수 없습니다.", userId);
            return 0; // 권한이 없으면 0 반환
        }

        return couponRepository.countAllByStrfIsNull();
    }
}
