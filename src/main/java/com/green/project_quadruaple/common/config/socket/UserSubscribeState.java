package com.green.project_quadruaple.common.config.socket;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class UserSubscribeState {
    
    // Set<cjId>
    public static final Set<Long> USER_SUB_STATE = new HashSet<>();
    public static final Map<Long, SseEmitter> ARTICLE_TO_CONNECTION = new ConcurrentHashMap<>();
}
