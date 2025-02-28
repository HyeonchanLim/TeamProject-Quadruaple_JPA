package com.green.project_quadruaple.trip;

import com.green.project_quadruaple.entity.model.TripLocation;
import com.green.project_quadruaple.entity.model.TripLocationId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripLocationRepository extends JpaRepository<TripLocation , TripLocationId> {
}
