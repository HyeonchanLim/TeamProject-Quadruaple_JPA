package com.green.project_quadruaple.strf;

import com.green.project_quadruaple.strf.model.GetNonDetail;
import com.green.project_quadruaple.strf.model.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StrfMapper {

    void strfUpsert(Long userId, Long strfId);
    StrfSelRes getMemberDetail(Long userId, Long strfId);
    List<StrfAmenity> strfAmenity (Long strfId,String category);
    List<StrfMenu> strfMenu(Long strfId);
    List<StrfParlorDto> strfParlor (Long strfId , String category );

}

