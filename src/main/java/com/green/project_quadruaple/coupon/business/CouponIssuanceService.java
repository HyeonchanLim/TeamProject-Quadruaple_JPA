package com.green.project_quadruaple.coupon.business;

import com.green.project_quadruaple.common.config.jwt.UserRole;
import com.green.project_quadruaple.coupon.model.CouponPostReq;
import com.green.project_quadruaple.coupon.repository.CouponRepository;
import com.green.project_quadruaple.entity.model.Coupon;
import com.green.project_quadruaple.entity.model.Role;
import com.green.project_quadruaple.entity.model.StayTourRestaurFest;
import com.green.project_quadruaple.entity.model.User;
import com.green.project_quadruaple.strf.StrfRepository;
import com.green.project_quadruaple.user.Repository.UserRepository;
import com.green.project_quadruaple.user.model.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CouponIssuanceService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CouponRepository couponRepository;
    private final StrfRepository strfRepository;

    public int insCoupon(CouponPostReq req, Long userId) {

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
}
