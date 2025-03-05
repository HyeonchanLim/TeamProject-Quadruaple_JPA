package com.green.project_quadruaple.businessmypage;

import com.green.project_quadruaple.businessmypage.model.BusinessMyPageBooking;
import com.green.project_quadruaple.businessmypage.model.BusinessMyPageBookingDetails;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BusinessMyPageMapper {
    List<BusinessMyPageBooking> selBookingByBusiness(Long userId);
    BusinessMyPageBookingDetails selBookingDetails(Long bookingId);
}
