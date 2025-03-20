package com.green.project_quadruaple.chat.service;

import com.green.project_quadruaple.booking.repository.BookingRepository;
import com.green.project_quadruaple.chat.model.dto.ChatDto;
import com.green.project_quadruaple.chat.model.dto.ChatRoomDto;
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
import com.green.project_quadruaple.entity.base.NoticeCategory;
import com.green.project_quadruaple.entity.model.*;
import com.green.project_quadruaple.notice.NoticeService;
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
    private final ChatRoomMapper chatRoomMapper;
    private final BookingRepository bookingRepository;
    private final ChatReceiveRepository chatReceiveRepository;
    private final NoticeService noticeService;

    @Transactional
    public ResponseWrapper<Long> createBookingChatRoom(PostChatRoomReq req) {

        long signedUserId = AuthenticationFacade.getSignedUserId();

        User hostUser = userRepository.findById(signedUserId).orElse(null);
        User inviteUser = userRepository.findByStrfId(req.getStrfId());

        Long bookingId = req.getBookingId();
        if(hostUser == null || inviteUser == null) {
            return new ResponseWrapper<>(ResponseCode.NOT_FOUND_USER.getCode(), null);
        }

        Booking booking = bookingRepository.findBookingAndChatRoomById(bookingId);
        ChatRoom chatRoom = booking.getChatRoom();
        if(chatRoom != null) { // 이미 존재하는 채팅방
            return new ResponseWrapper<>(ResponseCode.OK.getCode(), chatRoom.getChatRoomId());
        }

        chatRoom = ChatRoom.builder()
                .title(req.getTitle())
                .build();
        booking.setChatRoom(chatRoom);

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

        StayTourRestaurFest strf = strfRepository.findById(req.getStrfId()).orElse(null);
        String title = req.getTitle()+"채팅방에 참여하셨습니다.";
        StringBuilder contents = new StringBuilder(title)
                .append(strf.getTitle()).append("의 호스트 ").append(hostUser.getName()).append("님과 ")
                .append("매너를 지켜 즐겁게 대화해주세요.");
        noticeService.postNotice(NoticeCategory.CHAT,title,contents.toString(),inviteUser, chatRoom.getChatRoomId());

        return new ResponseWrapper<>(ResponseCode.OK.getCode(), chatRoom.getChatRoomId());
    }

    @Transactional
    public ResponseWrapper<Long> createAdminChatRoom() {

        long signedUserId = AuthenticationFacade.getSignedUserId();

        User hostUser = userRepository.findById(signedUserId).orElse(null);
        List<User> inviteUserList = userRepository.findByRole(UserRole.ADMIN);
        User inviteUser = null;
        if(!inviteUserList.isEmpty()) {
            inviteUser = inviteUserList.get(0);
        } else {
            throw new RuntimeException("어드민 없음");
        }

        List<ChatRoom> chatRoomWithAdminList = chatJoinRepository.findChatJoinsByUserIds(signedUserId, inviteUser.getUserId());
        ChatRoom chatRoomWithAdmin;
        if(chatRoomWithAdminList.isEmpty()) { // 채팅방 없으면 새로 생성
            chatRoomWithAdmin = ChatRoom.builder()
                    .title("관리자와의 채팅")
                    .build();

            ChatJoin hostUserJoin = ChatJoin.builder()
                    .user(hostUser)
                    .chatRoom(chatRoomWithAdmin)
                    .build();

            ChatJoin inviteUserJoin = ChatJoin.builder()
                    .user(inviteUser)
                    .chatRoom(chatRoomWithAdmin)
                    .build();

            chatJoinRepository.save(hostUserJoin);
            chatJoinRepository.save(inviteUserJoin);
        } else {
            chatRoomWithAdmin = chatRoomWithAdminList.get(0);
        }

        return new ResponseWrapper<>(ResponseCode.OK.getCode(), chatRoomWithAdmin.getChatRoomId());

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
        List<ChatRoomDto> allChatRoomDtoList = chatRoomMapper.selAllChatRoomList(signedUserId, role.getValue(), startIdx);

        for (ChatRoomDto allChatRoomDto : allChatRoomDtoList) {
            boolean flag = false;
            for (ChatRoomDto chatRoomDto : chatRoomDtoList) {
                if(chatRoomDto.getRoomId() == allChatRoomDto.getRoomId()) {
                    flag = true;
                    break;
                }
            }
            if(!flag) {
                chatRoomDtoList.add(allChatRoomDto);
            }
        }

        LocalDateTime now = LocalDateTime.now();
        for (ChatRoomDto chatRoomDto : chatRoomDtoList) {
            LocalDateTime latestChatDLT = chatRoomDto.getLatestChatLDT();
            chatRoomDto.setLastChatTime(formatDate(latestChatDLT, now));
        }
        return new ResponseWrapper<>(ResponseCode.OK.getCode(), chatRoomDtoList);
    }

    private String formatDate(LocalDateTime time, LocalDateTime now) {
        if(time == null) return null;
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
