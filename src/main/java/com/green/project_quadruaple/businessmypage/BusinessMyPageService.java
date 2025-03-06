package com.green.project_quadruaple.businessmypage;

import com.green.project_quadruaple.businessmypage.model.BusinessMyPageBooking;
import com.green.project_quadruaple.businessmypage.model.BusinessMyPageBookingDetails;
import com.green.project_quadruaple.businessmypage.model.BusinessMyPageSales;
import com.green.project_quadruaple.common.config.security.AuthenticationFacade;
import com.green.project_quadruaple.strf.StrfRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

@Slf4j
@Service
@RequiredArgsConstructor
public class BusinessMyPageService {
    private final BusinessMyPageMapper businessMyPageMapper;
    private final AuthenticationFacade authenticationFacade;
    private final StrfRepository strfRepository;

    public List<BusinessMyPageBooking> selBusinessMyPageBooking() {
        long userId = authenticationFacade.getSignedUserId();

        List<BusinessMyPageBooking> bookingList = businessMyPageMapper.selBookingByBusiness(userId);

        return bookingList;
    }

    public BusinessMyPageBookingDetails selBusinessMyPageBookingDetails(Long bookingId) {
        long userId = authenticationFacade.getSignedUserId();

        return businessMyPageMapper.selBookingDetails(bookingId, userId);
    }

    public BusinessMyPageSales selBusinessMyPageSales(Integer orderType) {
        long userId = authenticationFacade.getSignedUserId();

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
}
