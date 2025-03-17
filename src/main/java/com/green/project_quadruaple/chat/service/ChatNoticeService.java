package com.green.project_quadruaple.chat.service;

import com.green.project_quadruaple.chat.repository.ChatReceiveRepository;
import com.green.project_quadruaple.chat.repository.EmitterRepository;
import com.green.project_quadruaple.common.config.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ChatNoticeService {

    private final ChatReceiveRepository chatReceiveRepository;
    private final Map<Long, Set<SseEmitter>> articleToConnection = new ConcurrentHashMap<>();
    private final EmitterRepository emitterRepository;

    public SseEmitter connect() {
        long signedUserId = AuthenticationFacade.getSignedUserId();

        SseEmitter emitter = emitterRepository.save(signedUserId, new SseEmitter(Long.MAX_VALUE));

        try {
            boolean existsReceiveChat = chatReceiveRepository.findByUserId(signedUserId);
            SseEmitter.SseEventBuilder builder = SseEmitter.event()
                    .name("connect")
                    .data(existsReceiveChat)
                    .reconnectTime(3000L);
            emitter.send(builder);

            final Set<SseEmitter> connections = articleToConnection.getOrDefault(signedUserId, new HashSet<>());
            connections.add(emitter);
            articleToConnection.put(signedUserId, connections);

        } catch (Exception e) {
            emitter.complete();
            e.printStackTrace();
        }

        emitter.onCompletion(() -> emitterRepository.deleteById(signedUserId));
        emitter.onTimeout(() -> emitterRepository.deleteById(signedUserId));

        return emitter;
    }

    public void broadcastChatNotice() {

    }
}
