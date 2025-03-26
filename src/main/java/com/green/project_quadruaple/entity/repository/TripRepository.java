package com.green.project_quadruaple.entity.repository;

import com.green.project_quadruaple.entity.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Long> {
    @Query(value = "SELECT t FROM Trip t WHERE DATEDIFF(t.period.startAt, NOW()) = 7", nativeQuery = true)
    List<Trip> findTripBefore7days();
}
