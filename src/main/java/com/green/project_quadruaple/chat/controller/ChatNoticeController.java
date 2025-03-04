package com.green.project_quadruaple.chat.controller;

import com.green.project_quadruaple.chat.service.ChatNoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
@RequestMapping("chat-notice")
public class ChatNoticeController {

    private final ChatNoticeService chatNoticeService;

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subChatNotice() {
        return chatNoticeService.subChatNotice();
    }
}
