package com.green.project_quadruaple.entity.repository;

import com.green.project_quadruaple.entity.view.PointView;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface PointViewRepository extends JpaRepository<PointView, Long> {

    List<PointView> findByUserIdAndCategoryAndCreatedAtBetween(
            Long userId, int category, LocalDateTime startAt, LocalDateTime endAt, Sort sort
    );

    List<PointView> findByUserIdAndCreatedAtBetween(
            Long userId, LocalDateTime startAt, LocalDateTime endAt,  Sort sort
    );


    @Query("select ph.remainPoint from PointView ph where ph.userId=:userId order by ph.pointHistoryId desc limit 1")
    Integer findLastRemainPointByUserId(Long userId);
}
