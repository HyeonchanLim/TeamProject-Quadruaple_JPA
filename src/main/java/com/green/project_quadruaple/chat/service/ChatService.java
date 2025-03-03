package com.green.project_quadruaple.chat.service;

import com.green.project_quadruaple.chat.model.req.PostChatReq;
import com.green.project_quadruaple.chat.model.res.JoinRes;
import com.green.project_quadruaple.chat.model.res.MessageRes;
//import com.green.project_quadruaple.chat.repository.ChatJoinRepository;
import com.green.project_quadruaple.chat.repository.ChatJoinRepository;
import com.green.project_quadruaple.chat.repository.ChatRepository;
import com.green.project_quadruaple.chat.repository.ChatRoomRepository;
import com.green.project_quadruaple.common.config.jwt.JwtUser;
import com.green.project_quadruaple.common.config.jwt.UserRole;
import com.green.project_quadruaple.entity.model.*;
import com.green.project_quadruaple.user.Repository.UserRepository;
import com.green.project_quadruaple.user.model.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRepository chatRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatJoinRepository chatJoinRepository;
    private final RoleRepository roleRepository;

    // 유저 입장 시
    @Transactional
    public JoinRes joinChat(Long roomId, Principal principal) {

        Long signedUserId = getSignedUserId(principal);
        if(signedUserId == null) { // 인증 정보 없으면 return
            return JoinRes.builder()
                    .userName(null)
                    .error("유저 정보 없음")
                    .build();
        }

        try {
            // 채팅방에 참여중인 유저가 아니라면 저장.
            if(chatJoinRepository.existsJoinUser(roomId, signedUserId) <= 0) {
                Role role = roleRepository.findByUserIdAndRoleName(signedUserId, UserRole.USER);
                ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElse(null);
                ChatJoin chatJoin = ChatJoin.builder()
                        .chatRoom(chatRoom)
                        .role(role)
                        .build();
                chatJoinRepository.save(chatJoin);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    // 채팅 저장
    @Transactional
    public MessageRes insChat(PostChatReq req, Principal principal) {

        Long signedUserId = getSignedUserId(principal);
        if(signedUserId == null) {
            throw new RuntimeException("인증 정보 오류");
        }

        try {
            ChatJoin chatJoin = chatJoinRepository.findByChatRoomIdAndUserId(req.getRoomId(), signedUserId);
            if(chatJoin == null) {
                throw new RuntimeException("채팅방에 없는 유저");
            }
            Chat chat = Chat.builder()
                    .chatJoin(chatJoin)
                    .content(req.getMessage())
                    .build();
            chatRepository.save(chat);

            return MessageRes.builder()
                    .sender(chatJoin.getRole().getUser().getName())
                    .message(req.getMessage())
                    .createdAt(chat.getCreatedAt())
                    .build();

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    // principal -> JwtUser 변환
    private Long getSignedUserId(Principal principal) {
        Authentication authentication = (Authentication) principal;
        if (authentication.getPrincipal() instanceof JwtUser) {
            return ((JwtUser)authentication.getPrincipal()).getSignedUserId();
        }
        return null;
    }
}
