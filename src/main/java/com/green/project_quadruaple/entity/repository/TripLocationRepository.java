package com.green.project_quadruaple.entity.repository;

import com.green.project_quadruaple.entity.model.Location;
import com.green.project_quadruaple.entity.model.Trip;
import com.green.project_quadruaple.entity.model.TripLocation;
import com.green.project_quadruaple.entity.model.TripLocationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TripLocationRepository extends JpaRepository<TripLocation , TripLocationId> {
    @Query("SELECT tl.location FROM TripLocation tl WHERE tl.trip = :trip")
    List<Location> findLocationByTrip(Trip trip);
}
