package com.green.project_quadruaple.trip;

import com.green.project_quadruaple.entity.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface TripRepository extends JpaRepository<Trip, Long> {

}
