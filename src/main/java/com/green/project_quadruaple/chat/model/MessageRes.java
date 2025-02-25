package com.green.project_quadruaple.chat.model;

import lombok.*;

@Getter
@Setter
@Builder
public class MessageRes {

    private String sender;
    private String message;
    private String error;
}
