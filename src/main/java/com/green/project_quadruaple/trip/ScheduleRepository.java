package com.green.project_quadruaple.trip;

import com.green.project_quadruaple.entity.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
