package com.green.project_quadruaple.chat.service;

import com.green.project_quadruaple.chat.model.req.PostChatReq;
import com.green.project_quadruaple.chat.model.res.MessageRes;
//import com.green.project_quadruaple.chat.repository.ChatJoinRepository;
import com.green.project_quadruaple.chat.repository.ChatJoinRepository;
import com.green.project_quadruaple.chat.repository.ChatReceiveRepository;
import com.green.project_quadruaple.chat.repository.ChatRepository;
import com.green.project_quadruaple.common.config.jwt.JwtUser;
import com.green.project_quadruaple.common.config.security.AuthenticationFacade;
import com.green.project_quadruaple.entity.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.green.project_quadruaple.common.config.socket.UserSubscribeState.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final ChatJoinRepository chatJoinRepository;
    private final ChatReceiveRepository chatReceiveRepository;
    private final Map<Long, SseEmitter> emitterMap = new ConcurrentHashMap<>();

    // 유저 입장 시
    @Transactional
    public void joinChat(Long roomId, Principal principal) {
        Long signedUserId = getSignedUserId(principal);
        if(signedUserId == null) { // 인증 정보 없으면 return
            return;
        }

        try {
            // 채팅방에 참여중인 유저가 아니라면 저장.
            Long cjId = chatJoinRepository.findChatJoinIdByChatRoomIdAndUserId(roomId, signedUserId);
//            if(cjId == null) {
//                Role role = roleRepository.findByUserIdAndRoleName(signedUserId, UserRole.USER);
//                ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElse(null);
//                ChatJoin chatJoin = ChatJoin.builder()
//                        .chatRoom(chatRoom)
//                        .role(role)
//                        .build();
//                chatJoinRepository.save(chatJoin);
//                cjId = chatJoin.getCjId();
//            }
            if(cjId != null) {
                USER_SUB_STATE.add(cjId); // 채팅방 구독 시 구독상태 저장
                chatReceiveRepository.deleteAll(chatReceiveRepository.findByListenerId(cjId));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public SseEmitter connect() {
        long signedUserId = AuthenticationFacade.getSignedUserId();
        SseEmitter emitter = new SseEmitter(10000 * 60 * 60L);

        try {
            boolean existsReceiveChat = chatReceiveRepository.findByUserId(signedUserId);
            SseEmitter.SseEventBuilder builder = SseEmitter.event()
                    .name("connect")
                    .data(existsReceiveChat);
            emitter.send(builder);

            emitterMap.put(signedUserId, emitter);

        } catch (Exception e) {
            emitter.complete();
            e.printStackTrace();
        }

        emitter.onCompletion(() -> emitterMap.remove(signedUserId));
        emitter.onTimeout(() -> {
            emitterMap.remove(signedUserId);
            emitter.complete();
        });

        return emitter;
    }

    // 채팅 저장
    @Transactional
    public MessageRes insChat(PostChatReq req, Principal principal) {

        Long signedUserId = getSignedUserId(principal);

        if(signedUserId == null) {
            throw new RuntimeException("인증 정보 오류");
        }

        Long roomId = req.getRoomId();
        try {
            ChatJoin signedUserchatJoin = chatJoinRepository.findByChatRoomIdAndUserId(roomId, signedUserId);
            if(signedUserchatJoin == null) {
                throw new RuntimeException("채팅방에 없는 유저");
            }
            Chat chat = Chat.builder()
                    .chatJoin(signedUserchatJoin)
                    .content(req.getMessage())
                    .build();
            chatRepository.save(chat);

            List<ChatJoin> cjList = chatJoinRepository.findChatJoinIdListByChatRoomId(roomId);
            for (Long l : USER_SUB_STATE) {
                log.info("user : {}", l);
            }
            for (ChatJoin cj : cjList) {
                if(!USER_SUB_STATE.contains(cj.getCjId())) {
                    ChatReceive chatReceive = new ChatReceive();
                    chatReceive.setChat(chat);
                    chatReceive.setListenerId(cj);
                    chatReceiveRepository.save(chatReceive);

                    Long userId = cj.getUser().getUserId();
                    SseEmitter emitters = emitterMap.get(userId);
                    if(emitters != null) {
                        emitters.send(true);
                    }
                }
            }

            return MessageRes.builder()
                    .sender(signedUserchatJoin.getUser().getName())
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
