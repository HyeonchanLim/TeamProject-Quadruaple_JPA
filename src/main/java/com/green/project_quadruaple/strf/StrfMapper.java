package com.green.project_quadruaple.strf;

import com.green.project_quadruaple.strf.model.GetNonDetail;
import com.green.project_quadruaple.strf.model.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface StrfMapper {

    void strfUpsert(Long userId, Long strfId);
    StrfSelRes getMemberDetail(Long userId, Long strfId);
    StrfSelRes busiMemberDetail(Long userId, Long strfId);
    List<StrfAmenity> strfAmenity (Long strfId,String category);
    List<StrfMenu> strfMenu(Long strfId);
    List<StrfParlorDto> strfParlor (Long strfId , String category );

    int reviewCount(long strfId);
    List<StrfCouponGetRes> couponList(long strfId);
    int couponReceive (long userId , long couponId);


    List<StrfCheckRes> stayBookingExists(@Param("strfId") long strfId,
                              @Param("checkIn") LocalDateTime checkIn,
                            @Param("checkOut") LocalDateTime checkOut);


    List<Integer> getRestDaysByStrfId(@Param("strfId") Long strfId);
}

