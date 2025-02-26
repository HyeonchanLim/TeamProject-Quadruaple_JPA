package com.green.project_quadruaple.chat.repository;

import com.green.project_quadruaple.chat.model.dto.ChatDto;
import com.green.project_quadruaple.entity.model.ChatJoin;
import com.green.project_quadruaple.entity.model.ChatRoom;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    @Query("""
            SELECT new com.green.project_quadruaple.chat.model.dto.ChatDto(
                c.chatId
                , u.name
                , u.profilePic
                , CASE WHEN u.userId = :signedUserId THEN true ELSE false END
                , u.userId
                , c.content
                , cj.chatRoom.title
                , c.createdAt
            ) FROM Chat c
            JOIN c.chatJoin cj
            JOIN cj.user u
            WHERE cj.chatRoom.chatRoomId = :chatRoomId
            ORDER BY c.chatId ASC
            """)
    List<ChatDto> findChatLimit30(Long chatRoomId, Long signedUserId, Pageable pageable);
}
