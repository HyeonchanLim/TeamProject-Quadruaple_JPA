package com.green.project_quadruaple.trip;

import com.green.project_quadruaple.entity.model.TripUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripUserRepository extends JpaRepository<TripUser, Long> {
}
