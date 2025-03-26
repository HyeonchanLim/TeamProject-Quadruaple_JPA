package com.green.project_quadruaple.businessmypage;

import com.green.project_quadruaple.businessmypage.model.*;
import com.green.project_quadruaple.common.config.jwt.UserRole;
import com.green.project_quadruaple.common.config.security.AuthenticationFacade;
import com.green.project_quadruaple.entity.model.Role;
import com.green.project_quadruaple.entity.repository.StrfRepository;
import com.green.project_quadruaple.user.model.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

    public List<BusinessMyPageSales> selBusinessMyPageSales(String startMonth, String endMonth) {
        long userId = authenticationFacade.getSignedUserId();

        // 사용자 권한 확인 (BUSI 권한이 있는지 확인)
        List<Role> roles = roleRepository.findByUserUserId(userId);
        boolean isBusi = roles.stream().anyMatch(role -> role.getRole() == UserRole.BUSI);

        if (!isBusi) {
            return null;
        }

        // JPA를 사용하여 category 조회
        List<String> category = strfRepository.findCategoryByUserId(userId);

        // 날짜 형식 변환 (한 자리 월을 두 자리로 변환)
        startMonth = formatMonth(startMonth);
        endMonth = formatMonth(endMonth);

        // 유효한 날짜 범위 계산
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        YearMonth now = YearMonth.now();
        YearMonth oneYearAgo = now.minusMonths(12);

        YearMonth start = YearMonth.parse(startMonth, formatter);
        YearMonth end = YearMonth.parse(endMonth, formatter);

        // 시작월이 1년 전보다 과거면 1년 전으로 조정
        if (start.isBefore(oneYearAgo)) {
            start = oneYearAgo;
        }
        // 종료월이 현재보다 미래면 현재 월로 조정
        if (end.isAfter(now)) {
            end = now;
        }

        // category에 따라 적절한 메서드 호출
        if (category.contains("STAY")) {
            return businessMyPageMapper.selBusinessSTAYSales(userId, start.format(formatter), end.format(formatter));
        } else if (category.contains("RESTAUR")) {
            return businessMyPageMapper.selUsedPointsByBusinessRESTAUR(userId, start.format(formatter), end.format(formatter));
        }

        // 해당하는 category가 없을 경우 빈 객체 반환
        return new ArrayList<>();
    }

    private String formatMonth(String month) {
        try {
            String[] parts = month.split("-");
            if (parts.length == 2) {
                int year = Integer.parseInt(parts[0]);
                int monthNum = Integer.parseInt(parts[1]);
                return String.format("%04d-%02d", year, monthNum); // yyyy-MM 형식으로 변환
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("올바른 날짜 형식이 아닙니다. (예: 2024-06)", e);
        }
        return month; // 기본 반환 (문제가 없을 경우)
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
