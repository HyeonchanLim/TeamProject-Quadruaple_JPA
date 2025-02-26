package com.green.project_quadruaple.booking.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class BookingRes {
    private Long bookingId;
    private Long strfId;
    private String strfPic;
    private String createdAt;
    private String checkInDate;
    private String checkOutDate;
    private LocalDate checkInTime;
    private LocalDate checkOutTime;
    private int price;
    private int state;
    private Integer chatRoomId;
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
*/
