package com.green.project_quadruaple.chat.controller;

import com.green.project_quadruaple.chat.service.ChatService;
import com.green.project_quadruaple.chat.model.ChatDto;
import com.green.project_quadruaple.chat.model.JoinReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.join")
//    @SendTo("/topic/greetings")
    public void join(@Payload JoinReq req) {
        chatService.save();
        messagingTemplate.convertAndSend("/sub/chat/" + req.roomId(), String.format( "[%s]채팅방 입장", "user1"));
    }

    @MessageMapping("/chat.sendMessage")
//    @SendTo("/topic/greetings")
    public void sendMessage(@Payload ChatDto req) {
        log.info("[{}] message : [{}]", req.getSender(), req.getMessage());
//        chatService.save(req);
        messagingTemplate.convertAndSend("/sub/chat/" + req.getRoomId(), req.getMessage());
    }
}
