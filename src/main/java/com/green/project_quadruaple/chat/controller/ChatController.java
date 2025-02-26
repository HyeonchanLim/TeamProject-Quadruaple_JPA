package com.green.project_quadruaple.chat.controller;

import com.green.project_quadruaple.chat.model.JoinRes;
import com.green.project_quadruaple.chat.model.MessageRes;
import com.green.project_quadruaple.chat.service.ChatService;
import com.green.project_quadruaple.chat.model.ChatDto;
import com.green.project_quadruaple.chat.model.JoinReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.join")
//    @SendTo("/topic/greetings")
    public void join(@Payload JoinReq req, Principal principal) {
        MessageRes res = chatService.joinChat(req, principal);
        messagingTemplate.convertAndSend("/sub/chat/" + req.roomId(), res);
    }

    @MessageMapping("/chat.sendMessage")
//    @SendTo("/topic/greetings")
    public void sendMessage(@Payload ChatDto req,
                            @Header("Authorization") String token) {
        log.info("[{}] message : [{}]", req.getSender(), req.getMessage());
        log.info("token : {}", token);
        MessageRes res = chatService.insChat(req);
        messagingTemplate.convertAndSend("/sub/chat/" + req.getRoomId(), req);
    }
}
