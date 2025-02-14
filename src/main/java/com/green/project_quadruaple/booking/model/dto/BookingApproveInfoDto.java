package com.green.project_quadruaple.booking.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BookingApproveInfoDto {

    private String checkIn;
    private String checkOut;
    private String title;
    private String userName;
}
