package com.green.project_quadruaple.trip;

import com.green.project_quadruaple.entity.model.ScheduleMemo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheMemoRepository extends JpaRepository<ScheduleMemo, Long> {
}
