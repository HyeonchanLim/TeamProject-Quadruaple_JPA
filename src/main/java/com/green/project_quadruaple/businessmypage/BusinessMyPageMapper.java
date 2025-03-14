package com.green.project_quadruaple.businessmypage;

import com.green.project_quadruaple.businessmypage.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface BusinessMyPageMapper {
    List<BusinessMyPageBooking> selBookingByBusiness(Long userId, String startDate, String endDate);
    BusinessMyPageBookingDetails selBookingDetails(Long bookingId, Long userId);
    BusinessMyPageSales selBusinessSTAYSales(Integer orderType, Long userId, List<Integer> monthOffsets);
    BusinessMyPageSales selUsedPointsByBusinessRESTAUR(Integer orderType, Long userId, List<Integer> monthOffsets);

    Long selTotalPointAmount(Long userId);
    List<BusinessMyPagePointDetail> selPointDetailList(Long userId);

    default List<Integer> calculateMonthOffsets(Integer orderType) {
        List<Integer> monthOffsets = new ArrayList<>();
        for (int i = 0; i < orderType; i++) {
            monthOffsets.add(i);
        }
        return monthOffsets;
    }
}
