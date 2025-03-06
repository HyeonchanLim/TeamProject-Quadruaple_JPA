package com.green.project_quadruaple.businessmypage;

import com.green.project_quadruaple.businessmypage.model.BusinessMyPageBooking;
import com.green.project_quadruaple.businessmypage.model.BusinessMyPageBookingDetails;
import com.green.project_quadruaple.common.config.security.AuthenticationFacade;
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

    public List<BusinessMyPageBooking> selBusinessMyPageBooking() {
        long userId = authenticationFacade.getSignedUserId();

        List<BusinessMyPageBooking> bookingList = businessMyPageMapper.selBookingByBusiness(userId);

        return bookingList;
    }

    public BusinessMyPageBookingDetails selBusinessMyPageBookingDetails(Long bookingId) {
        return businessMyPageMapper.selBookingDetails(bookingId);
    }
}
