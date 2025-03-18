package com.green.project_quadruaple.common.config.socket;

import com.green.project_quadruaple.chat.repository.ChatJoinRepository;
import com.green.project_quadruaple.chat.repository.ChatRoomRepository;
import com.green.project_quadruaple.common.config.jwt.JwtUser;
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
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class StompChannelInterceptor implements ChannelInterceptor {

    private final TokenProvider tokenProvider;
    private final static String HEADER_AUTHORIZATION = "Authorization";
    private final static String TOKEN_PREFIX = "Bearer ";
    private final String simpUser = "simpUser";
    private final ChatRoomRepository chatRoomRepository;
    private final ChatJoinRepository chatJoinRepository;

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

            if (token != null) {
                try {
                    // 토큰에서 Authentication 객체를 얻어옴
                    Authentication auth = tokenProvider.getAuthentication(token);
                    if (auth != null) {
                        accessor.setUser(auth);
                        if(messageType.equals("SUBSCRIBE")) {
                            String simpDestination = String.valueOf(accessor.getHeader("simpDestination"));
                            Long roomId = Long.parseLong(simpDestination.substring(simpDestination.lastIndexOf('/')+1));
                            if(!chatRoomRepository.existsById(roomId)) {
                                throw new IllegalStateException("존재하지 않는 채팅방");
                            };
                        }
                    } else {
                        log.error("Authentication is null.");
                    }
                } catch (ExpiredJwtException e) {
                    e.printStackTrace();
                    throw new IllegalStateException(e);
                }
            }
            // 구독 해제 시 Set에 구독 상태 삭제
        } else if (messageType.equals("UNSUBSCRIBE")) {
            Long signedUserId = getSignedUserId(accessor.getUser());
            String subPath = String.valueOf(accessor.getHeader("simpSubscriptionId"));
            Long roomId = Long.parseLong(subPath.substring(subPath.lastIndexOf('/') + 1));
            Long cjId = chatJoinRepository.findChatJoinIdByChatRoomIdAndUserId(roomId, signedUserId);
            UserSubscribeState.USER_SUB_STATE.remove(cjId);
        }
        return message;
    }

    private String getAccessToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) { // 문자열이 TOKEN_PREFIX 로 시작하는지 확인
            return authorizationHeader.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

    private Long getSignedUserId(Principal principal) {
        Authentication authentication = (Authentication) principal;
        if (authentication.getPrincipal() instanceof JwtUser) {
            return ((JwtUser)authentication.getPrincipal()).getSignedUserId();
        }
        return null;
    }
}
