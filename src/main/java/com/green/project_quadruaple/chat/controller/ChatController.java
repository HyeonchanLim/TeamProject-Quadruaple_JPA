package com.green.project_quadruaple.chat.controller;

import com.green.project_quadruaple.chat.model.*;
import com.green.project_quadruaple.chat.model.req.JoinReq;
import com.green.project_quadruaple.chat.model.req.PostChatReq;
import com.green.project_quadruaple.chat.model.res.JoinRes;
import com.green.project_quadruaple.chat.model.res.MessageRes;
import com.green.project_quadruaple.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

//    @MessageMapping("/chat.join")
//    public void join(@Payload JoinReq req, Principal principal) {
//        JoinRes res = chatService.joinChat(req, principal);
//        messagingTemplate.convertAndSend("/sub/chat/" + req.getRoomId(), String.format( "[%s]채팅방 입장", res.getUserName()));
//    }

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload PostChatReq req, Principal principal) {

        MessageRes res = chatService.insChat(req, principal);
        messagingTemplate.convertAndSend("/sub/chat/" + req.getRoomId(), res);
    }

    @MessageMapping("/chat.setStatus")
    public void setStatus(@Payload ChatStatus req) {
        log.info("req : {}", req);
        messagingTemplate.convertAndSend("/sub/chat/status", req);
    }

    @SubscribeMapping("/chat/{roomId}")
    public String subChat(@DestinationVariable Long roomId, Principal principal) {
        log.info("roomId : {}", roomId);
        chatService.joinChat(roomId, principal);
        return "1w";
    }
}
