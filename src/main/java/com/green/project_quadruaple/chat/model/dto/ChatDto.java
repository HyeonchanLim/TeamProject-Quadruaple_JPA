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
    private String senderName;
    private Long senderId;
    private String senderPic;
    private boolean signedUser;
    private String message;
    private String title;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;


    public ChatDto(Long chatId, String senderName, String senderPic
            , boolean signedUser, Long senderId, String message, String title, LocalDateTime createdAt) {
        this.chatId = chatId;
        this.senderName = senderName;
        this.senderPic = senderPic;
        this.signedUser = signedUser;
        this.senderId = senderId;
        this.message = message;
        this.title = title;
        this.createdAt = createdAt;
    }
}