package com.green.project_quadruaple.booking.model;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BookingRefundReq {
    private final Long bookingId;
}

/*
* 예약PK(booking_id) : 1
* */