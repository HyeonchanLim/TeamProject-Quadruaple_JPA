package com.green.project_quadruaple.entity.repository;

import com.green.project_quadruaple.entity.model.ScheduleMemo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheMemoRepository extends JpaRepository<ScheduleMemo, Long> {
    boolean existsByTrip_TripId(Long tripId);
}
