package com.green.project_quadruaple.businessmypage;

import com.green.project_quadruaple.businessmypage.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface BusinessMyPageMapper {
    List<BusinessMyPageBooking> selBookingByBusiness(Long userId, String startDate, String endDate);
    BusinessMyPageBookingDetails selBookingDetails(Long bookingId, Long userId);
    List<BusinessMyPageSales> selBusinessSTAYSales(Long userId, String startMonth, String endMonth);
    List<BusinessMyPageSales> selUsedPointsByBusinessRESTAUR(Long userId, String startMonth, String endMonth);

    Long selTotalPointAmount(Long userId);
    List<BusinessMyPagePointDetail> selPointDetailList(Long userId);
}
