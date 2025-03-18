package com.green.project_quadruaple.chat.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class ChatRoomDto {

    private Long roomId;
    private String title;
    private String latestChat;
    private String lastChatTime;
    private String pic;
    private Long userId;
    private Integer unreadChat;


    @JsonIgnore
    private LocalDateTime latestChatLDT;

    public ChatRoomDto(Long roomId, String title, String latestChat, LocalDateTime latestChatDLT, String pic, Integer unreadChat, Long userId) {
        this.roomId = roomId;
        this.title = title;
        this.latestChat = latestChat;
        this.latestChatLDT = latestChatDLT;
        this.pic = pic;
        this.unreadChat = unreadChat;
        this.userId = userId;
    }
}
