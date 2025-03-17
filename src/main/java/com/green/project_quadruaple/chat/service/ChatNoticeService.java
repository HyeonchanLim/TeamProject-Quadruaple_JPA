package com.green.project_quadruaple.chat.service;

import com.green.project_quadruaple.chat.repository.ChatReceiveRepository;
import com.green.project_quadruaple.common.config.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


import static com.green.project_quadruaple.common.config.socket.UserSubscribeState.*;

@Service
@RequiredArgsConstructor
public class ChatNoticeService {

    private final ChatReceiveRepository chatReceiveRepository;

    public SseEmitter connect() {
        long signedUserId = AuthenticationFacade.getSignedUserId();
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);

        try {
            boolean existsReceiveChat = chatReceiveRepository.findByUserId(signedUserId);
            SseEmitter.SseEventBuilder builder = SseEmitter.event()
                    .name("connect")
                    .data(existsReceiveChat)
                    .reconnectTime(3000L);
            emitter.send(builder);

            ARTICLE_TO_CONNECTION.put(signedUserId, emitter);

        } catch (Exception e) {
             // 연결 끊기면 세션에서 삭제
            emitter.complete();
            e.printStackTrace();
        }

        emitter.onCompletion(() -> ARTICLE_TO_CONNECTION.remove(signedUserId));
        emitter.onTimeout(() -> ARTICLE_TO_CONNECTION.remove(signedUserId));

        return emitter;
    }
}
