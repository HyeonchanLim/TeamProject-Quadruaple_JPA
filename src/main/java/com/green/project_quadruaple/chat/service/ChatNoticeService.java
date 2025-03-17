package com.green.project_quadruaple.chat.service;

import com.green.project_quadruaple.chat.repository.ChatReceiveRepository;
import com.green.project_quadruaple.common.config.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.green.project_quadruaple.common.config.socket.UserSubscribeState.*;

@Service
@RequiredArgsConstructor
public class ChatNoticeService {

    private final ChatReceiveRepository chatReceiveRepository;
    private final Map<Long, SseEmitter> emitterMap = new ConcurrentHashMap<>();

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

            emitterMap.put(signedUserId, emitter);

        } catch (Exception e) {
            emitter.complete();
            e.printStackTrace();
        }

        emitter.onCompletion(() -> emitterMap.remove(signedUserId));
        emitter.onTimeout(() -> emitterMap.remove(signedUserId));

        return emitter;
    }
}
