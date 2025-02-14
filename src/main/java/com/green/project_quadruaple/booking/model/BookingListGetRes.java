package com.green.project_quadruaple.booking.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Schema
public class BookingListGetRes {
    private long bookingId;
    private String checkIn;
    private String checkOut;
    private String checkInDayOfWeek;
    private String checkOutDayOfWeek;
    private long menuId;
    private int menuPrice;
    private int finalPayment;
    private String strfTitle;
    private String strfPic;
    private int reviewStatus;
    private String bookingStatus; // "progress" 또는 "completed"

}

/*
*         "bookingId": 1,
        "checkIn": "2025-01-02 (목) 14:00",
        "checkOut": "2025-01-03 (금) 22:00",
        "menuId": 0,
        "menuPrice": 0,
        "finalPayment": 225000,
        "strfTitle": "맹종죽테마공원",
        "strfPic": null,
        "reviewStatus": 0,
        "bookingStatus": "completed"
* */
