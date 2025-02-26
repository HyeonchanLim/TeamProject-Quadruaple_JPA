package com.green.project_quadruaple.booking.repository;

import com.green.project_quadruaple.booking.model.dto.BookingRes;
import com.green.project_quadruaple.entity.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

//    @Query("""
//            select new com.green.project_quadruaple.booking.model.dto.BookingRes() from Booking b
//
//
//            """)
//    List<BookingRes> findBookingListByUserId(Long userId);
}
