package com.green.project_quadruaple.strf;

import com.green.project_quadruaple.strf.model.GetNonDetail;
import com.green.project_quadruaple.strf.model.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface StrfMapper {

    StrfSelRes getMemberDetail(Long userId, String strfId);
    void strfUpsert(Long userId, String strfId);

    GetNonDetail getNonMemberDetail (Long strfId);
}

