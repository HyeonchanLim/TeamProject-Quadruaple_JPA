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
    private String strfTitle;
    private String strfPic;
    private String createdAt;
    private String checkInDate; // (2025-01-01 수)
    private String checkOutDate; // (2025-01-01 수)
    private Integer price;
    private Integer state;
    private Long chatRoomId;

    private String checkInTime; // (14:00)
    private String checkOutTime; // (14:00)
    // DB 에서 가져와서 (2025-01-01 수) 형태로 변환
    @JsonIgnore
    private LocalDateTime createdAtLD;
    @JsonIgnore
    private LocalDateTime checkInDateLD;
    @JsonIgnore
    private LocalDateTime checkOutDateLD;

    public BookingRes(Long bookingId, Long strfId, String strfTitle, String strfPic,
                      LocalDateTime createdAtLD, LocalDateTime checkInDateLD, LocalDateTime checkOutDateLD,
                      int price, int state, Long chatRoomId)
    {
        this.bookingId = bookingId;
        this.strfId = strfId;
        this.strfTitle = strfTitle;
        this.strfPic = strfPic;
        this.createdAtLD = createdAtLD;
        this.checkInDateLD = checkInDateLD;
        this.checkOutDateLD = checkOutDateLD;
        this.price = price;
        this.state = state;
        this.chatRoomId = chatRoomId;
    }
}


