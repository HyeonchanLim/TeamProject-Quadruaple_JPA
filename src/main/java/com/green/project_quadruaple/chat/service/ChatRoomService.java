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
    public ResponseWrapper<Long> postChatRoom(ChatRoomReq req) {

        User user = userRepository.findById(124L).isPresent() ? userRepository.findById(124L).get() : null;

        ChatRoom chatRoom = ChatRoom.builder()
                .title(req.getTitle())
                .build();
        chatRoomRepository.save(chatRoom);

        ChatJoin chatJoin = ChatJoin.builder()
                .user(user)
                .chatRoom(chatRoom)
                .build();
        chatJoinRepository.save(chatJoin);

        return new ResponseWrapper<>(ResponseCode.OK.getCode(), chatRoom.getChatRoomId());
    }
}
