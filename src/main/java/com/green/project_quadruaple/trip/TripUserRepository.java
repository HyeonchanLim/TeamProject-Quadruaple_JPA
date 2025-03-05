package com.green.project_quadruaple.trip;

import com.green.project_quadruaple.entity.model.Trip;
import com.green.project_quadruaple.entity.model.TripUser;
import com.green.project_quadruaple.entity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TripUserRepository extends JpaRepository<TripUser, Long> {
    boolean existsByUser_userIdAndTrip_tripIdAndDisable(Long userId, Long tripId, int disable);

    @Query("select tu.user from TripUser tu where tu.trip=:trip")
    List<User> findUserByTrip(Trip trip);
}
