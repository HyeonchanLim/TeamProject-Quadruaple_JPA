package com.green.project_quadruaple.booking.repository;

import com.green.project_quadruaple.booking.model.dto.BookingRes;
import com.green.project_quadruaple.entity.model.Booking;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("""
            select new com.green.project_quadruaple.booking.model.dto.BookingRes(
                b.bookingId
                , strf.strfId
                , strf.title
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

    @Query("""
            update Booking b
            set b.state = 2
            where b.state = 1
                and b.checkOut < :now
            """)
    void updateAllStateAfterCheckOut(LocalDateTime now);


    @Query(value = "SELECT * FROM Booking b WHERE DATEDIFF(b.check_in, NOW()) = 1 AND b.state = 1", nativeQuery = true)
    List<Booking> findBookingBeforeExpired();
}