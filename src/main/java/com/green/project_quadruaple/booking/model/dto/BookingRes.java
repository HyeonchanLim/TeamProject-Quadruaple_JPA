package com.green.project_quadruaple.booking.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
public class BookingRes {
    private Long bookingId;
    private Long strfId;
    private String strfPic;
    private String createdAt;
    private String checkInDate;
    private String checkOutDate;
    private Integer price;
    private Integer state;
    private Long chatRoomId;

    private LocalTime checkInTime;
    private LocalTime checkOutTime;

    // DB 에서 가져와서 (2025-01-01 수) 형태로 변환
    @JsonIgnore
    private LocalDateTime createdAtLD;
    @JsonIgnore
    private LocalDateTime checkInDateLD;
    @JsonIgnore
    private LocalDateTime checkOutDateLD;

    public BookingRes(Long bookingId, Long strfId, String strfPic,
                      LocalDateTime createdAtLD, LocalDateTime checkInDateLD, LocalDateTime checkOutDateLD,
                      int price, int state, Long chatRoomId)
    {
        this.bookingId = bookingId;
        this.strfId = strfId;
        this.strfPic = strfPic;
        this.createdAtLD = createdAtLD;
        this.checkInDateLD = checkInDateLD;
        this.checkOutDateLD = checkOutDateLD;
        this.price = price;
        this.state = state;
        this.chatRoomId = chatRoomId;
    }
}


