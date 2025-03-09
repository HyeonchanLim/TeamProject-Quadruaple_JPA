package com.green.project_quadruaple.booking.repository;

import com.green.project_quadruaple.booking.model.dto.BookingRes;
import com.green.project_quadruaple.entity.model.Booking;
import com.green.project_quadruaple.entity.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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
                , b.totalPayment
                , b.state
                , b.chatRoom.chatRoomId
            ) from Booking b
            join b.menu.stayTourRestaurFest strf
            left join StrfPic sp
                on strf.strfId = sp.strfId.strfId
            where b.user.userId = :userId
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


    @Query("""
        SELECT DISTINCT b
        FROM Booking b
        LEFT JOIN FETCH b.menu m
        LEFT join fetch b.user u
        LEFT JOIN FETCH m.stayTourRestaurFest s
        WHERE DATEDIFF(b.checkIn, NOW()) = 1
        AND b.state = 1
    """)
    List<Booking> findBookingBeforeExpired();

    @Query("""
    select b from Booking b
    left join fetch b.chatRoom
    where b.bookingId = :bookingId
    """)
    Booking findBookingAndChatRoomById(Long bookingId);

    @Query("""
        select bn.user from Booking b
        join b.menu m
        join m.stayTourRestaurFest strf
        join strf.busiNum bn
        where b.bookingId = :bookingId
        """)
    User findBusiUserIdByBookingId(Long bookingId);
}