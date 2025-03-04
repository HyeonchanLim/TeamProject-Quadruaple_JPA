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
    private Integer unreadChat;


    @JsonIgnore
    private LocalDateTime latestChatDLT;

    public ChatRoomDto(Long roomId, String title, String latestChat, LocalDateTime latestChatDLT, String pic, Integer unreadChat) {
        this.roomId = roomId;
        this.title = title;
        this.latestChat = latestChat;
        this.latestChatDLT = latestChatDLT;
        this.pic = pic;
        this.unreadChat = unreadChat;
    }
}
