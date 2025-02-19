package com.green.project_quadruaple.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(MessageReq req) {
        log.info("req: {}", req);
//        String send = chatService.message(req);
        return new Greeting(HtmlUtils.htmlEscape(req.name()));
    }
}
