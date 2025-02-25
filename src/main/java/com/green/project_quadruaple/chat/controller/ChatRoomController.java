package com.green.project_quadruaple.chat.controller;

import com.green.project_quadruaple.chat.service.ChatRoomService;
import com.green.project_quadruaple.chat.model.ChatRoomReq;
import com.green.project_quadruaple.common.model.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("chat-room")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @PostMapping
    public ResponseWrapper<Long> postChatRoom(@RequestBody ChatRoomReq req) {
        return chatRoomService.createChatRoom(req);
    }
}
