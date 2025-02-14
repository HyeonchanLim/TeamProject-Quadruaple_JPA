package com.green.project_quadruaple.booking.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class BookingInsDto {

    private Long bookingId;
    private Integer actualPaid;
    private String checkIn;
    private String checkOut;
    private Long userId;
    private List<MenuIdAndQuantityDto> orderList;
    private String tid;
}
