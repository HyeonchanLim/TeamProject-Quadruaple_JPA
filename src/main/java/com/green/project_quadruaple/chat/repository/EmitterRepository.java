package com.green.project_quadruaple.chat.repository;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class EmitterRepository {

    private final Map<Long, SseEmitter> emitterMap = new ConcurrentHashMap<>();

    public SseEmitter findById(Long id) {
        return emitterMap.get(id);
    }

    public SseEmitter save(Long id, SseEmitter emitter) {
        emitterMap.put(id, emitter);
        return emitterMap.get(id);
    }

    public void deleteById(Long id) {
        emitterMap.remove(id);
    }
}
