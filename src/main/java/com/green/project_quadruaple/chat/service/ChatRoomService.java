package com.green.project_quadruaple.chat.service;

import com.green.project_quadruaple.chat.model.dto.ChatDto;
import com.green.project_quadruaple.chat.model.dto.ChatRoomDto;
import com.green.project_quadruaple.chat.model.req.GetChatRoomReq;
import com.green.project_quadruaple.chat.model.req.PostChatRoomReq;
import com.green.project_quadruaple.chat.repository.ChatJoinRepository;
import com.green.project_quadruaple.chat.repository.ChatRoomRepository;
import com.green.project_quadruaple.common.config.enumdata.ResponseCode;
import com.green.project_quadruaple.common.config.security.AuthenticationFacade;
import com.green.project_quadruaple.common.model.ResponseWrapper;
//import com.green.project_quadruaple.entity.model.ChatJoin;
import com.green.project_quadruaple.entity.model.ChatRoom;
import com.green.project_quadruaple.entity.model.Role;
import com.green.project_quadruaple.entity.model.User;
import com.green.project_quadruaple.strf.StrfRepository;
import com.green.project_quadruaple.user.Repository.UserRepository;
import com.green.project_quadruaple.user.model.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final ChatJoinRepository chatJoinRepository;
    private final StrfRepository strfRepository;
    private final RoleRepository roleRepository;

    @Transactional
    public ResponseWrapper<Long> createChatRoom(PostChatRoomReq req) {

        long signedUserId = AuthenticationFacade.getSignedUserId();

        Role hostUserRole = roleRepository.findByUserIdAndRoleName(signedUserId, "USER");
        Role inviteUserRole = roleRepository.findByUserIdAndRoleName(signedUserId, "BUSI");

        if(hostUserRole == null || inviteUserRole == null) {
            return new ResponseWrapper<>(ResponseCode.NOT_FOUND_USER.getCode(), null);
        }

        ChatRoom chatRoom = ChatRoom.builder()
                .title(req.getTitle())
                .build();
        chatRoomRepository.save(chatRoom);
        chatRoomRepository.flush();

//        ChatJoin hostUserJoin = ChatJoin.builder()
//                .role(hostUserRole)
//                .chatRoom(chatRoom)
//                .build();
//        chatJoinRepository.save(hostUserJoin);
//
//        ChatJoin inviteUserJoin = ChatJoin.builder()
//                .role(inviteUserRole)
//                .chatRoom(chatRoom)
//                .build();
//        chatJoinRepository.save(inviteUserJoin);

        return new ResponseWrapper<>(ResponseCode.OK.getCode(), chatRoom.getChatRoomId());
    }

    private Role getRole(List<Role> roleList, String value) {
        for (Role role : roleList) {
            if(role.getRole().getValue().equals(value)) {
                return role;
            }
        }
        return null;
    }

    // 채팅방 불러오기
    public ResponseWrapper<List<ChatDto>> getChatList(Long roomId, GetChatRoomReq req) {
        long signedUserId = AuthenticationFacade.getSignedUserId();

        PageRequest pageRequest = PageRequest.of(req.getPage(), 30);
        LocalDateTime now = LocalDateTime.now();

        List<ChatDto> chatLimit30 = chatRoomRepository.findChatLimit30(roomId, signedUserId, pageRequest);
        for (ChatDto chatDto : chatLimit30) {
            LocalDateTime createdAtLD = chatDto.getCreatedAtLD();

            Period diffYMD = Period.between(createdAtLD.toLocalDate(), now.toLocalDate());


            if(diffYMD.getYears() != 0) {
                String StringAt = diffYMD.getYears() + "년전";
                chatDto.setCreatedAt(StringAt);
            }
            else if(diffYMD.getMonths() != 0) {
                String StringAt = diffYMD.getMonths() + "개월전";
                chatDto.setCreatedAt(StringAt);
            }
            else if(diffYMD.getDays() != 0) {
                String StringAt = diffYMD.getDays() + "일전";
                chatDto.setCreatedAt(StringAt);
            } else {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("a hh:mm", Locale.KOREA);
                String StringAt = createdAtLD.format(formatter);
                chatDto.setCreatedAt(StringAt);
            }
        }
        return new ResponseWrapper<>(ResponseCode.OK.getCode(), chatLimit30);
    }

    public ResponseWrapper<List<ChatRoomDto>> getChatRoomList(int page) {
        long signedUserId = AuthenticationFacade.getSignedUserId();

        return null;
    }
}
