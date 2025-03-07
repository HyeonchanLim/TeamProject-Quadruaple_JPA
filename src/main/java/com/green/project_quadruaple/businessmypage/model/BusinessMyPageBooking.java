package com.green.project_quadruaple.businessmypage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

@ToString
@Getter
@Setter
public class BusinessMyPageBooking {
    private Long bookingId;
    private Long strfId;
    private String title;
    private String picName;
    @JsonIgnore
    private LocalDateTime checkIn;
    @JsonIgnore
    private LocalDateTime checkOut;
    private int totalPayment;
    private String state;

    // 포맷팅된 날짜와 시간
    private String checkInDate; // (2025-01-01 수)
    private String checkOutDate; // (2025-01-01 수)
    private String checkInTime; // (14:00)
    private String checkOutTime; // (14:00)

    // 생성자에서 날짜와 시간을 포맷팅
    public BusinessMyPageBooking(Long bookingId, Long strfId, String title, String picName,
                                 LocalDateTime checkIn, LocalDateTime checkOut,
                                 int totalPayment, String state) {
        this.bookingId = bookingId;
        this.strfId = strfId;
        this.title = title;
        this.picName = picName;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.totalPayment = totalPayment;
        this.state = state;

        // 날짜와 시간 포맷팅
        this.checkInDate = formatDateTime(checkIn);
        this.checkOutDate = formatDateTime(checkOut);
        this.checkInTime = formatTime(checkIn);
        this.checkOutTime = formatTime(checkOut);
    }

    // 날짜를 포맷팅하는 유틸리티 메서드 (날짜와 요일 포함)
    private String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "";
        }
        String dayOfWeek = dateTime.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREA);
        String formattedDate = dateTime.toLocalDate().toString() + " " + dayOfWeek;
        return formattedDate;
    }

    // 시간을 포맷팅하는 유틸리티 메서드 (HH:mm 형식)
    private String formatTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "";
        }
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        return dateTime.format(timeFormatter);
    }
}
