package com.green.project_quadruaple.coupon.business;

import com.green.project_quadruaple.common.config.jwt.UserRole;
import com.green.project_quadruaple.common.config.security.AuthenticationFacade;
import com.green.project_quadruaple.coupon.model.CouponGetDto;
import com.green.project_quadruaple.coupon.model.CouponPostReq;
import com.green.project_quadruaple.coupon.model.CouponUpdateDto;
import com.green.project_quadruaple.coupon.repository.CouponRepository;
import com.green.project_quadruaple.entity.model.Coupon;
import com.green.project_quadruaple.entity.model.Role;
import com.green.project_quadruaple.entity.model.StayTourRestaurFest;
import com.green.project_quadruaple.entity.model.User;
import com.green.project_quadruaple.strf.StrfRepository;
import com.green.project_quadruaple.user.Repository.UserRepository;
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
public class CouponIssuanceService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CouponRepository couponRepository;
    private final StrfRepository strfRepository;
    private final AuthenticationFacade authenticationFacade;

    public int insCoupon(CouponPostReq req) {
        long userId = authenticationFacade.getSignedUserId();

        // 사용자 권한 확인 (BUSI 권한이 있는지 확인)
        List<Role> roles = roleRepository.findByUserUserId(userId);
        boolean isBusi = roles.stream().anyMatch(role -> role.getRole() == UserRole.BUSI);

        if (!isBusi) {
            log.error("쿠폰 발급 권한이 없습니다. 사용자 권한: {}", roles.isEmpty() ? "없음" : roles.get(0).getRole());
            return 0;  // 권한이 없으면 쿠폰 발급하지 않음
        }

        // 관련된 StayTourRestaurFest 정보 조회
        StayTourRestaurFest stayTourRestaurFest = strfRepository.findById(req.getStrfId())
            .orElseThrow(() -> new RuntimeException("Invalid StayTourRestaurFest ID"));

        // 해당 사업자가 올린 게시물인지 확인
        if (!stayTourRestaurFest.getBusiNum().getUser().getUserId().equals(userId)) {
            log.error("해당 사업자가 올린 게시물이 아닙니다. 사용자 ID: {}", userId);
            return 0;  // 사업자가 올린 게시물이 아니면 쿠폰 발급하지 않음
        }

        // 쿠폰 객체 생성
        Coupon coupon = new Coupon();
        coupon.setTitle(req.getTitle());
        coupon.setExpiredAt(req.getExpiredAt());
        coupon.setDiscountPer(req.getDiscountPer());
        coupon.setDistributeAt(req.getDistributeAt());
        coupon.setStrf(stayTourRestaurFest);

        // 쿠폰 발급
        couponRepository.save(coupon);

        log.info("쿠폰 발급 성공: {}", coupon.getTitle());
        return 1;
    }

    @Transactional
    public List<CouponGetDto> getCouponsByUser() {
        long userId = authenticationFacade.getSignedUserId();

        // 모든 쿠폰 조회
        List<Coupon> coupons = couponRepository.findAll();  // 모든 쿠폰을 가져오기

        // 로그인한 유저가 발급한 쿠폰만 필터링
        List<CouponGetDto> userCoupons = coupons.stream()
                .filter(coupon -> {
                    // coupon.getStrf()가 null인지 체크
                    StayTourRestaurFest strf = coupon.getStrf();
                    if (strf != null && strf.getBusiNum() != null && strf.getBusiNum().getUser() != null) {
                        // BusinessNum의 User와 비교
                        return strf.getBusiNum().getUser().getUserId().equals(userId);
                    }
                    return false;  // strf 또는 비즈니스 번호가 없으면 무시
                })
                .map(CouponGetDto::new)
                .collect(Collectors.toList());

        if (userCoupons.isEmpty()) {
            log.warn("유저 {}가 발급한 쿠폰이 없습니다.", userId);
            return new ArrayList<>();
        }

        return userCoupons;
    }

    @Transactional
    public int updCoupon(CouponUpdateDto req) {
        long userId = authenticationFacade.getSignedUserId();

        // 쿠폰 조회 (존재하지 않으면 예외 발생)
        Coupon coupon = couponRepository.findById(req.getCouponId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 쿠폰입니다."));

        // 해당 쿠폰이 사용자가 발급한 쿠폰인지 확인
        StayTourRestaurFest stayTourRestaurFest = coupon.getStrf();
        if (!stayTourRestaurFest.getBusiNum().getUser().getUserId().equals(userId)) {
            log.error("쿠폰 수정 권한이 없습니다. 사용자 ID: {}", userId);
            return 0;  // 권한이 없으면 수정하지 않음
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
}
