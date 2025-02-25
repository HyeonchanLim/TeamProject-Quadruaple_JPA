package com.green.project_quadruaple.booking;

import com.green.project_quadruaple.entity.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookingRepository extends JpaRepository<Booking, Long> {

}
