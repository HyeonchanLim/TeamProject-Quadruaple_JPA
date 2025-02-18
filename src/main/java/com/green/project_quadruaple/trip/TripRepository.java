package com.green.project_quadruaple.trip;

import com.green.project_quadruaple.entity.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRepository extends JpaRepository<Trip, Long> {
}
