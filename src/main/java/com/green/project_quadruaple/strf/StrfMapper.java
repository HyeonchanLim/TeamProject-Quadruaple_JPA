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

    // ✅ 예약 여부 확인 (예약 존재 시 true 반환)
    boolean stayBookingExists(@Param("menuId") Long menuId,
                            @Param("checkIn") LocalDateTime checkIn,
                            @Param("checkOut") LocalDateTime checkOut);

    // ✅ 해당 숙소의 휴무일(요일) 리스트 가져오기
    List<Integer> getRestDaysByStrfId(@Param("strfId") Long strfId);
}

