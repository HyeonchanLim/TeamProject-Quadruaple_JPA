package com.green.project_quadruaple.businessmypage;

import com.green.project_quadruaple.businessmypage.model.BusinessMyPageBooking;
import com.green.project_quadruaple.businessmypage.model.BusinessMyPageBookingDetails;
import com.green.project_quadruaple.businessmypage.model.BusinessMyPagePointList;
import com.green.project_quadruaple.businessmypage.model.BusinessMyPageSales;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface BusinessMyPageMapper {
    List<BusinessMyPageBooking> selBookingByBusiness(Long userId);
    BusinessMyPageBookingDetails selBookingDetails(Long bookingId, Long userId);
    BusinessMyPageSales selBusinessSTAYSales(Integer orderType, Long userId, List<Integer> monthOffsets);
    BusinessMyPageSales selUsedPointsByBusinessRESTAUR(Integer orderType, Long userId, List<Integer> monthOffsets);
    List<BusinessMyPagePointList> selPointList(Long userId);

    default List<Integer> calculateMonthOffsets(Integer orderType) {
        List<Integer> monthOffsets = new ArrayList<>();
        for (int i = 0; i < orderType; i++) {
            monthOffsets.add(i);
        }
        return monthOffsets;
    }
}
