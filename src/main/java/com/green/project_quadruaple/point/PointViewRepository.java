package com.green.project_quadruaple.point;

import com.green.project_quadruaple.entity.model.PointHistory;
import com.green.project_quadruaple.entity.model.PointView;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface PointViewRepository extends JpaRepository<PointView, Long> {

    List<PointView> findByUserIdAndCategoryAndCreatedAtBetween(
            Long userId, int category, LocalDateTime startAt, LocalDateTime endAt, Pageable pageable
    );

    List<PointView> findByUserIdAndCreatedAtBetween(
            Long userId, LocalDateTime startAt, LocalDateTime endAt, Pageable pageable
    );


    @Query("select ph.remainPoint from PointView ph where ph.userId=:userId order by ph.pointHistoryId desc limit 1")
    Integer findLastRemainPointByUserId(Long userId);

    @Query("""
        select p from PointView p
        where p.userId = :signedUserId
        order by p.pointHistoryId desc
        """)
    List<PointHistory> findPointHistoriesByUserId(Long signedUserId, Pageable pageable);
}
