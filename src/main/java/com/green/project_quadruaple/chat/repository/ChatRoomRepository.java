package com.green.project_quadruaple.chat.repository;

import com.green.project_quadruaple.chat.model.dto.ChatDto;
import com.green.project_quadruaple.chat.model.dto.ChatRoomDto;
import com.green.project_quadruaple.common.config.jwt.UserRole;
import com.green.project_quadruaple.entity.model.ChatRoom;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    @Query("""
            SELECT new com.green.project_quadruaple.chat.model.dto.ChatDto(
                c.chatId
                , u.userId
                , u.name
                , u.profilePic
                , CASE WHEN u.userId = :signedUserId THEN true ELSE false END
                , c.content
                , cj.chatRoom.title
                , c.createdAt
            ) FROM Chat c
            JOIN c.chatJoin cj
            JOIN cj.user u
            WHERE cj.chatRoom.chatRoomId = :chatRoomId
            ORDER BY c.chatId DESC
            """)
    List<ChatDto> findChatLimit30(Long chatRoomId, Long signedUserId, Pageable pageable);

    @Query("""
        select cr from ChatRoom cr
        join Booking b on b.chatRoom.chatRoomId = cr.chatRoomId
        where b.bookingId = :bookingId
        """)
    ChatRoom findByBookingId(Long bookingId);
}
