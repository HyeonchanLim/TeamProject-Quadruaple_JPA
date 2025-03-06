package com.green.project_quadruaple.chat.service;

import com.green.project_quadruaple.chat.repository.ChatReceiveRepository;
import com.green.project_quadruaple.chat.repository.EmitterRepository;
import com.green.project_quadruaple.common.config.security.AuthenticationFacade;
import com.green.project_quadruaple.entity.model.ChatReceive;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ChatNoticeService {

    private final ChatReceiveRepository chatReceiveRepository;
    private final EmitterRepository emitterRepository;

    public SseEmitter subChatNotice() {
        long signedUserId = AuthenticationFacade.getSignedUserId();

        SseEmitter emitter = emitterRepository.save(signedUserId, new SseEmitter(Long.MAX_VALUE));

        ScheduledExecutorService thread = new ScheduledThreadPoolExecutor(1);
        thread.scheduleAtFixedRate(() -> {
            try {
                boolean exists = chatReceiveRepository.findByUserId(signedUserId);
                if(exists) {
                    emitter.send(true);
                }
            } catch (Exception e) {
                emitter.complete();
                e.printStackTrace();
                thread.shutdown();
            }
        }, 0, 30, TimeUnit.SECONDS);

        emitter.onCompletion(() -> emitterRepository.deleteById(signedUserId));
        emitter.onTimeout(() -> emitterRepository.deleteById(signedUserId));

        return emitter;
    }
}
