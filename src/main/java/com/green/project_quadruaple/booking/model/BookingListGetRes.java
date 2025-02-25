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
    private Long bookingId;
    private Long strfId;
    private String startPic;
    private String createdAt;
    private String checkInDate;
    private String checkOutDate;
    private String checkInTime;
    private String checkOutTime;
    private Integer price;
    private Integer refund;
}
