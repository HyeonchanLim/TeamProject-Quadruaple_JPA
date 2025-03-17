package com.green.project_quadruaple.chat.controller;

import com.green.project_quadruaple.chat.model.*;
import com.green.project_quadruaple.chat.model.req.JoinReq;
import com.green.project_quadruaple.chat.model.req.PostChatReq;
import com.green.project_quadruaple.chat.model.res.JoinRes;
import com.green.project_quadruaple.chat.model.res.MessageRes;
import com.green.project_quadruaple.chat.service.ChatService;
import com.green.project_quadruaple.common.config.socket.UserSubscribeState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.security.Principal;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.join")
    public void join(@Payload JoinReq req, Principal principal) {
        JoinRes res = JoinRes.builder().userName(principal.getName()).build();
        messagingTemplate.convertAndSend("/sub/chat/" + req.getRoomId(), res);
    }

    @ResponseBody
    @GetMapping(value = "chat-notice", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter chatNoticeConnection() {
        return chatService.connect();
    }

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload PostChatReq req, Principal principal) {

        MessageRes res = chatService.insChat(req, principal);
        messagingTemplate.convertAndSend("/sub/chat/" + req.getRoomId(), res);
    }

    @SubscribeMapping("/chat/{roomId}")
    public void subChat(@DestinationVariable Long roomId, Principal principal) {
        log.info("roomId : {}", roomId);
        chatService.joinChat(roomId, principal);
    }
}
