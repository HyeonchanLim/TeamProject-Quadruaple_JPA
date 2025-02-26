package com.green.project_quadruaple.recent.repository;

import com.green.project_quadruaple.entity.model.Recent;
import com.green.project_quadruaple.entity.model.RecentId;
import com.green.project_quadruaple.search.model.SearchBasicPopualarRes;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface RecentRepository extends JpaRepository<Recent , RecentId> {
    @Modifying
    @Query("UPDATE Recent r SET r.undoRecent = 1 WHERE r.id.userId = :userId AND r.id.strfId = :strfId")
    int hideRecent(@Param("userId") Long userId, @Param("strfId") Long strfId);

    @Modifying
    @Query("UPDATE Recent r SET r.undoRecent = 1 WHERE r.id.userId = :userId")
    int hideAllRecent(@Param("userId") Long userId);

//    @Query("""
//        SELECT new com.green.project_quadruaple.search.dto.SearchBasicPopularRes(
//            s.title, COUNT(r), s.strfId
//        )
//        FROM Recent r
//        JOIN r.strfId s
//        WHERE r.inquiredAt >= CURRENT_DATE - 90
//        GROUP BY s.title, s.strfId
//        ORDER BY COUNT(r) DESC
//    """)
//    List<SearchBasicPopualarRes> findPopularSearches();

}
