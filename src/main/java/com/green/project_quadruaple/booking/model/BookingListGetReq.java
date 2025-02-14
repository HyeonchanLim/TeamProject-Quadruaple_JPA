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
@Schema(title = "예약 목록 조회")
public class BookingListGetReq {
    private long userId;
}
