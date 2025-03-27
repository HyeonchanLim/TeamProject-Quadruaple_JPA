package com.green.project_quadruaple.chat.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ChatDto {
    private Long chatId;
    private Long userId;
    private String senderName;
    private String senderPic;
    private boolean signedUser;
    private String message;
    private String title;
    private String createdAt;

    @JsonIgnore
    private LocalDateTime createdAtLD;



    public ChatDto(Long chatId, Long userId, String senderName, String senderPic
            , boolean signedUser, String message, String title, LocalDateTime createdAtLD) {
        this.chatId = chatId;
        this.userId = userId;
        this.senderName = senderName;
        this.senderPic = senderPic;
        this.signedUser = signedUser;
        this.message = message;
        this.title = title;
        this.createdAtLD = createdAtLD;
    }
}