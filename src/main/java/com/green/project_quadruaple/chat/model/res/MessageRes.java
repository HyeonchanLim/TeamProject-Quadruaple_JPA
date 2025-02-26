package com.green.project_quadruaple.chat.model.res;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class MessageRes {

    private String sender;
    private String message;
    private LocalDateTime createdAt;
    private String error;
}
