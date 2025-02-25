package com.green.project_quadruaple.chat.service;

import com.green.project_quadruaple.chat.model.ChatRoomReq;
import com.green.project_quadruaple.chat.repository.ChatJoinRepository;
import com.green.project_quadruaple.chat.repository.ChatRoomRepository;
import com.green.project_quadruaple.common.config.enumdata.ResponseCode;
import com.green.project_quadruaple.common.model.ResponseWrapper;
import com.green.project_quadruaple.entity.model.ChatJoin;
import com.green.project_quadruaple.entity.model.ChatRoom;
import com.green.project_quadruaple.entity.model.User;
import com.green.project_quadruaple.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final ChatJoinRepository chatJoinRepository;

    @Transactional
    public ResponseWrapper<Long> createChatRoom(ChatRoomReq req) {

        User hostUser = userRepository.findById(124L).isPresent() ? userRepository.findById(124L).get() : null;
        User inviteUser = userRepository.findById(131L).isPresent() ? userRepository.findById(131L).get() : null;

        ChatRoom chatRoom = ChatRoom.builder()
                .title(req.getTitle())
                .build();
        chatRoomRepository.save(chatRoom);

        ChatJoin hostUserJoin = ChatJoin.builder()
                .user(hostUser)
                .chatRoom(chatRoom)
                .build();
        chatJoinRepository.save(hostUserJoin);

        ChatJoin inviteUserJoin = ChatJoin.builder()
                .user(inviteUser)
                .chatRoom(chatRoom)
                .build();
        chatJoinRepository.save(inviteUserJoin);

        return new ResponseWrapper<>(ResponseCode.OK.getCode(), chatRoom.getChatRoomId());
    }
}
