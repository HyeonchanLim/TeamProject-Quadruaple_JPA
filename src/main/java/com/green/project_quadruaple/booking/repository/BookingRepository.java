package com.green.project_quadruaple.booking.repository;

import com.green.project_quadruaple.booking.model.dto.BookingRes;
import com.green.project_quadruaple.entity.model.Booking;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("""
            select new com.green.project_quadruaple.booking.model.dto.BookingRes(
                b.bookingId
                , strf.strfId
                , sp.picName
                , b.createdAt
                , b.checkIn
                , b.checkOut
                , b.finalPayment
                , b.state
                , b.chatRoom.chatRoomId
            ) from Booking b
            join b.menu.stayTourRestaurFest strf
            left join StrfPic sp
                on strf.strfId = sp.strfId.strfId
            group by b.bookingId
            order by b.checkIn asc
            """)
    List<BookingRes> findBookingListByUserId(Long userId, Pageable pageable);
}
/*
        *  "bookingId": 1,
        "strfId": 3,
        "strfPic": "image.jpg",
        "createdAt": "2025-06-21 수",
        "checkInDate": "2025-07-08 토",
        "checkOutDate": "2025-07-09 일",
        "checkInTime": "14:00",
        "checkOutTime": "11:00",
        "price": 225000,
        "state" : 1,
        "chatRoomId" : 1 or null
        *
        *         this.bookingId = bookingId;
        this.strfId = strfId;
        this.strfPic = strfPic;
        this.createdAtLD = createdAtLD;
        this.checkInDateLD = checkInDateLD;
        this.checkOutDateLD = checkOutDateLD;
        this.price = price;
        this.state = state;
        this.chatRoomId = chatRoomId;
*/