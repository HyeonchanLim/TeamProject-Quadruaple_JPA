package com.green.project_quadruaple.recent;

import com.green.project_quadruaple.recent.model.RecentGetListRes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RecentMapper {
    List<RecentGetListRes> recentList (Long userId);
    int recentHide (Long userId , @Param("strf_id") Long strfId);
    int recentAllHide (Long userId);
}
