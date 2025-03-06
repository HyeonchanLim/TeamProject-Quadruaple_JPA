package com.green.project_quadruaple.chat.service;

import com.green.project_quadruaple.chat.model.dto.ChatDto;
import com.green.project_quadruaple.chat.model.dto.ChatRoomDto;
import com.green.project_quadruaple.chat.model.req.GetChatRoomReq;
import com.green.project_quadruaple.chat.model.req.PostChatRoomReq;
import com.green.project_quadruaple.chat.repository.ChatJoinRepository;
import com.green.project_quadruaple.chat.repository.ChatReceiveRepository;
import com.green.project_quadruaple.chat.repository.ChatRoomMapper;
import com.green.project_quadruaple.chat.repository.ChatRoomRepository;
import com.green.project_quadruaple.common.config.enumdata.ResponseCode;
import com.green.project_quadruaple.common.config.jwt.UserRole;
import com.green.project_quadruaple.common.config.security.AuthenticationFacade;
import com.green.project_quadruaple.common.model.ResponseWrapper;
//import com.green.project_quadruaple.entity.model.ChatJoin;
import com.green.project_quadruaple.entity.model.ChatJoin;
import com.green.project_quadruaple.entity.model.ChatRoom;
import com.green.project_quadruaple.entity.model.Role;
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
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final ChatJoinRepository chatJoinRepository;
    private final StrfRepository strfRepository;
    private final RoleRepository roleRepository;
    private final ChatRoomMapper chatRoomMapper;
    private final ChatReceiveRepository chatReceiveRepository;

    @Transactional
    public ResponseWrapper<Long> createChatRoom(PostChatRoomReq req) {

        long signedUserId = AuthenticationFacade.getSignedUserId();

        Role hostUserRole = roleRepository.findByUserIdAndRoleName(signedUserId, UserRole.USER);
        Role inviteUserRole = roleRepository.findByStrfIdAndRoleName(req.getStrfId(), UserRole.BUSI);

        if(hostUserRole == null || inviteUserRole == null) {
            return new ResponseWrapper<>(ResponseCode.NOT_FOUND_USER.getCode(), null);
        }

        ChatRoom chatRoom = ChatRoom.builder()
                .title(req.getTitle())
                .build();
        chatRoomRepository.save(chatRoom);
        chatRoomRepository.flush();

        ChatJoin hostUserJoin = ChatJoin.builder()
                .role(hostUserRole)
                .chatRoom(chatRoom)
                .build();
        chatJoinRepository.save(hostUserJoin);

        ChatJoin inviteUserJoin = ChatJoin.builder()
                .role(inviteUserRole)
                .chatRoom(chatRoom)
                .build();
        chatJoinRepository.save(inviteUserJoin);

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

    @Transactional
    public ResponseWrapper<List<ChatDto>> getChatList(Long roomId, int page) {
        long signedUserId = AuthenticationFacade.getSignedUserId();

        PageRequest pageRequest = PageRequest.of(page, 30);
        List<ChatDto> chatLimit30 = chatRoomRepository.findChatLimit30(roomId, signedUserId, pageRequest);
        LocalDateTime now = LocalDateTime.now();
        for (ChatDto chatDto : chatLimit30) {
            LocalDateTime createdAtLD = chatDto.getCreatedAtLD();
            chatDto.setCreatedAt(formatDate(createdAtLD, now));
        }
        try {
            chatReceiveRepository.deleteByUserId(signedUserId, roomId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }

        return new ResponseWrapper<>(ResponseCode.OK.getCode(), chatLimit30);
    }

    // 채팅방 불러오기
    public ResponseWrapper<List<ChatRoomDto>> getChatRoomList(int page, String roleReq) {
        long signedUserId = AuthenticationFacade.getSignedUserId();

        UserRole role = UserRole.getByValue(roleReq);
        if(role == null) {
            return new ResponseWrapper<>(ResponseCode.BAD_REQUEST.getCode(), null);
        }
        int startIdx = page * 10;
        List<ChatRoomDto> chatRoomDtoList = chatRoomMapper.selChatRoomList(signedUserId, role.getValue(), startIdx);
        LocalDateTime now = LocalDateTime.now();
        for (ChatRoomDto chatRoomDto : chatRoomDtoList) {
            LocalDateTime latestChatDLT = chatRoomDto.getLatestChatDLT();
            chatRoomDto.setLastChatTime(formatDate(latestChatDLT, now));
        }
        return new ResponseWrapper<>(ResponseCode.OK.getCode(), chatRoomDtoList);
    }

    private String formatDate(LocalDateTime time, LocalDateTime now) {
        Period diffYMD = Period.between(time.toLocalDate(), now.toLocalDate());

        String StringAt;

        if(diffYMD.getYears() != 0) {
            StringAt = diffYMD.getYears() + "년전";
        }
        else if(diffYMD.getMonths() != 0) {
            StringAt = diffYMD.getMonths() + "개월전";
        }
        else if(diffYMD.getDays() != 0) {
            StringAt = diffYMD.getDays() + "일전";
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("a hh:mm", Locale.KOREA);
            StringAt = time.format(formatter);
        }
        return StringAt;
    }
}
