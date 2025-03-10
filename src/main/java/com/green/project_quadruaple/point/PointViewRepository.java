package com.green.project_quadruaple.point;

import com.green.project_quadruaple.entity.model.PointHistory;
import com.green.project_quadruaple.entity.model.PointView;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PointViewRepository extends JpaRepository<PointView, Long> {

    List<PointView> findByUserIdAndCategoryAndCreatedAtBetween(
            Long userId, int category, LocalDate startAt, LocalDate endAt, Pageable pageable
    );

    List<PointView> findByUserIdAndCreatedAtBetween(
            Long userId, LocalDate startAt, LocalDate endAt, Pageable pageable
    );

}
