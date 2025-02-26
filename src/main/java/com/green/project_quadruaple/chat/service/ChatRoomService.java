package com.green.project_quadruaple.chat.service;

import com.green.project_quadruaple.chat.model.dto.ChatDto;
import com.green.project_quadruaple.chat.model.req.GetChatRoomReq;
import com.green.project_quadruaple.chat.model.req.PostChatRoomReq;
import com.green.project_quadruaple.chat.repository.ChatJoinRepository;
import com.green.project_quadruaple.chat.repository.ChatRoomRepository;
import com.green.project_quadruaple.common.config.enumdata.ResponseCode;
import com.green.project_quadruaple.common.config.security.AuthenticationFacade;
import com.green.project_quadruaple.common.model.ResponseWrapper;
import com.green.project_quadruaple.entity.model.ChatJoin;
import com.green.project_quadruaple.entity.model.ChatRoom;
import com.green.project_quadruaple.entity.model.User;
import com.green.project_quadruaple.strf.StrfRepository;
import com.green.project_quadruaple.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final ChatJoinRepository chatJoinRepository;
    private final StrfRepository strfRepository;

    @Transactional
    public ResponseWrapper<Long> createChatRoom(PostChatRoomReq req) {

        long signedUserId = AuthenticationFacade.getSignedUserId();

        User hostUser = userRepository.findById(signedUserId).orElse(null);
        User inviteUser = strfRepository.findBusiIdById(req.getStrfId());

        if(hostUser == null || inviteUser == null) {
            return new ResponseWrapper<>(ResponseCode.NOT_FOUND_USER.getCode(), null);
        }

        ChatRoom chatRoom = ChatRoom.builder()
                .title(req.getTitle())
                .build();
        chatRoomRepository.save(chatRoom);
        chatRoomRepository.flush();

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

    // 채팅방 불러오기
    public ResponseWrapper<List<ChatDto>> getChatRoom(Long roomId, GetChatRoomReq req) {
        long signedUserId = AuthenticationFacade.getSignedUserId();

        PageRequest pageRequest = PageRequest.of(req.getPage(), 30);
        List<ChatDto> chatLimit30 = chatRoomRepository.findChatLimit30(roomId, signedUserId, pageRequest);
        return new ResponseWrapper<>(ResponseCode.OK.getCode(), chatLimit30);
    }
}
