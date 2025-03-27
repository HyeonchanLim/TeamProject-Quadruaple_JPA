package com.green.project_quadruaple.booking.model.dto;

import com.green.project_quadruaple.entity.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookingHostMessage {
    private String title;
    private LocalDateTime checkIn;
    private String name;
    private User user;
}
