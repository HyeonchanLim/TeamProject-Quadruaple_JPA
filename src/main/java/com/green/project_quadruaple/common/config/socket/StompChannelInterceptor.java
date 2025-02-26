package com.green.project_quadruaple.common.config.socket;

import com.green.project_quadruaple.common.config.jwt.TokenProvider;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class StompChannelInterceptor implements ChannelInterceptor {

    private final TokenProvider tokenProvider;
    private final static String HEADER_AUTHORIZATION = "Authorization";
    private final static String TOKEN_PREFIX = "Bearer ";

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        List<String> authHeaders = accessor.getNativeHeader(HEADER_AUTHORIZATION);
        log.info("authHeaders = {}",authHeaders);

        if (authHeaders == null || authHeaders.isEmpty()) return message;

        String authToken = authHeaders.get(0);
        String token = getAccessToken(authToken);

        if (token != null) {
            try {
                // 토큰에서 Authentication 객체를 얻어옴
                Authentication auth = tokenProvider.getAuthentication(token);
                if (auth != null) {
                    accessor.setUser(auth);
                } else {
                    log.warn("Authentication is null.");
                }
            } catch (ExpiredJwtException e) {
                log.error("Error during authentication", e);
            }
        }

        log.info("authToken : {}", authToken);
        return message;
    }

    private String getAccessToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) { // 문자열이 TOKEN_PREFIX 로 시작하는지 확인
            return authorizationHeader.substring(TOKEN_PREFIX.length());
        }
        return null;
    }
}
