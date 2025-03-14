package com.green.project_quadruaple.businessmypage;

import com.green.project_quadruaple.businessmypage.model.*;
import com.green.project_quadruaple.common.config.jwt.UserRole;
import com.green.project_quadruaple.common.config.security.AuthenticationFacade;
import com.green.project_quadruaple.entity.model.Role;
import com.green.project_quadruaple.strf.StrfRepository;
import com.green.project_quadruaple.user.model.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BusinessMyPageService {
    private final BusinessMyPageMapper businessMyPageMapper;
    private final AuthenticationFacade authenticationFacade;
    private final StrfRepository strfRepository;
    private final RoleRepository roleRepository;

    public List<BusinessMyPageBooking> selBusinessMyPageBooking(String startDate, String endDate) {
        long userId = authenticationFacade.getSignedUserId();

        // 사용자 권한 확인 (BUSI 권한이 있는지 확인)
        List<Role> roles = roleRepository.findByUserUserId(userId);
        boolean isBusi = roles.stream().anyMatch(role -> role.getRole() == UserRole.BUSI);

        if (!isBusi) {
            return null;
        }

        List<BusinessMyPageBooking> bookingList = businessMyPageMapper.selBookingByBusiness(userId, startDate, endDate);

        return bookingList;
    }

    public BusinessMyPageBookingDetails selBusinessMyPageBookingDetails(Long bookingId) {
        long userId = authenticationFacade.getSignedUserId();

        // 사용자 권한 확인 (BUSI 권한이 있는지 확인)
        List<Role> roles = roleRepository.findByUserUserId(userId);
        boolean isBusi = roles.stream().anyMatch(role -> role.getRole() == UserRole.BUSI);

        if (!isBusi) {
            return null;
        }

        return businessMyPageMapper.selBookingDetails(bookingId, userId);
    }

    public BusinessMyPageSales selBusinessMyPageSales(Integer orderType) {
        long userId = authenticationFacade.getSignedUserId();

        // 사용자 권한 확인 (BUSI 권한이 있는지 확인)
        List<Role> roles = roleRepository.findByUserUserId(userId);
        boolean isBusi = roles.stream().anyMatch(role -> role.getRole() == UserRole.BUSI);

        if (!isBusi) {
            return null;
        }

        // JPA를 사용하여 category 조회
        List<String> category = strfRepository.findCategoryByUserId(userId);

        // orderType에 맞는 monthOffsets 계산
        List<Integer> monthOffsets = businessMyPageMapper.calculateMonthOffsets(orderType);

        // category에 따라 적절한 메서드 호출
        if (category.contains("STAY")) {
            return businessMyPageMapper.selBusinessSTAYSales(orderType, userId, monthOffsets);
        } else if (category.contains("RESTAUR")) {
            // monthOffsets도 함께 전달
            return businessMyPageMapper.selUsedPointsByBusinessRESTAUR(orderType, userId, monthOffsets);
        }

        // 해당하는 category가 없을 경우 빈 객체 반환
        return new BusinessMyPageSales();
    }

    public BusinessMyPagePointList selBusinessMyPagePointList() {
        long userId = authenticationFacade.getSignedUserId();

        // 사용자 권한 확인 (BUSI 권한이 있는지 확인)
        List<Role> roles = roleRepository.findByUserUserId(userId);
        boolean isBusi = roles.stream().anyMatch(role -> role.getRole() == UserRole.BUSI);

        if (!isBusi) {
            return null;
        }

        // 개별 포인트 내역 조회
        List<BusinessMyPagePointDetail> details = businessMyPageMapper.selPointDetailList(userId);

        // 전체 합계 조회
        Long totalAmount = businessMyPageMapper.selTotalPointAmount(userId);
        totalAmount = (totalAmount != null) ? totalAmount : 0L;

        // 결과 매핑
        BusinessMyPagePointList result = new BusinessMyPagePointList();
        result.setPointDetails(details);
        result.setTotalAmount(totalAmount);

        return result;
    }
}
