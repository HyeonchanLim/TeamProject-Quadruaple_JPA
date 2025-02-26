package com.green.project_quadruaple.common.config.socket;

import com.green.project_quadruaple.chat.repository.ChatRoomRepository;
import com.green.project_quadruaple.common.config.jwt.TokenProvider;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class StompChannelInterceptor implements ChannelInterceptor {

    private final TokenProvider tokenProvider;
    private final static String HEADER_AUTHORIZATION = "Authorization";
    private final static String TOKEN_PREFIX = "Bearer ";
    private final ChatRoomRepository chatRoomRepository;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        List<String> authHeaders = accessor.getNativeHeader(HEADER_AUTHORIZATION);
        log.info("authHeaders = {}",authHeaders);
        String messageType = Optional.ofNullable((SimpMessageType)accessor.getHeader("simpMessageType")).orElseThrow().name();
        if(messageType.equals("CONNECT") || messageType.equals("SUBSCRIBE")) {
            if (authHeaders == null || authHeaders.isEmpty()) {
                throw new IllegalStateException("인증 헤더 없음");
            }
            log.info("messageType = {}","CONNECT");
            String authToken = authHeaders.get(0);
            String token = getAccessToken(authToken);

            if(messageType.equals("SUBSCRIBE")) {
                String simpDestination = String.valueOf(accessor.getHeader("simpDestination"));
                Long roomId = Long.parseLong(simpDestination.substring(simpDestination.length()-1));
                if(!chatRoomRepository.existsById(roomId)) {
                    throw new IllegalStateException("존재하지 않는 채팅방");
                };
            }

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
                    log.error("토큰 에러", e);
                    throw new IllegalStateException();
                }
            }
        }
        return message;
    }

    private String getAccessToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) { // 문자열이 TOKEN_PREFIX 로 시작하는지 확인
            return authorizationHeader.substring(TOKEN_PREFIX.length());
        }
        return null;
    }
}
