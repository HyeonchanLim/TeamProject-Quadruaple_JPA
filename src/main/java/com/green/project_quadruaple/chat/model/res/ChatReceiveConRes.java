package com.green.project_quadruaple.chat.model.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Getter
@AllArgsConstructor
public class ChatReceiveConRes {
    private Long id;
    private boolean existsReceiveChat;
}
