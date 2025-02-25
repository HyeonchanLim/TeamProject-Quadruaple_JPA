package com.green.project_quadruaple.trip;

import com.green.project_quadruaple.entity.model.Trip;
import com.green.project_quadruaple.entity.model.TripUser;
import com.green.project_quadruaple.entity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TripUserRepository extends JpaRepository<TripUser, Long> {
    boolean existsByUserAndTripAndDisable(User user, Trip trip, int disable);
}
