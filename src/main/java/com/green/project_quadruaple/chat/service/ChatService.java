package com.green.project_quadruaple.chat.service;

import com.green.project_quadruaple.chat.model.ChatDto;
import com.green.project_quadruaple.chat.model.JoinReq;
import com.green.project_quadruaple.chat.model.JoinRes;
import com.green.project_quadruaple.chat.model.MessageRes;
import com.green.project_quadruaple.common.config.jwt.JwtUser;
import com.green.project_quadruaple.common.config.security.AuthenticationFacade;
import com.green.project_quadruaple.entity.model.User;
import com.green.project_quadruaple.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private final UserRepository userRepository;

    @Transactional
    public MessageRes joinChat(JoinReq req, Principal principal) {

        Long signedUserId = getSignedUserId(principal);
        if(signedUserId == null) { // 인증 정보 없으면 return
            return MessageRes.builder()
                    .error("유저 정보 없음")
                    .build();
        }
        User user = userRepository.findById(signedUserId).get();
        return MessageRes.builder()
                .sender(user.getName())
                .message(String.format("[%s] 채팅방 입장"))
                .build();
    }

    public MessageRes insChat(ChatDto req) {
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
