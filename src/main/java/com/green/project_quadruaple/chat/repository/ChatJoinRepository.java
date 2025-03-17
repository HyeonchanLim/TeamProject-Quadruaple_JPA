package com.green.project_quadruaple.chat.repository;

import com.green.project_quadruaple.entity.model.ChatJoin;
import com.green.project_quadruaple.entity.model.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatJoinRepository extends JpaRepository<ChatJoin, Long> {

    @Query("""
            select cj from ChatJoin cj
            where cj.chatRoom.chatRoomId = :chatRoomId
                and cj.user.userId = :userId
            """)
    ChatJoin findByChatRoomIdAndUserId(Long chatRoomId, Long userId);

    @Query("""
        select cj.cjId from ChatJoin cj
        where cj.chatRoom.chatRoomId = :roomId
            and cj.user.userId = :signedUserId
        """)
    Long findChatJoinIdByChatRoomIdAndUserId(Long roomId, Long signedUserId);

    @Query("""
        select cj from ChatJoin cj
        join fetch cj.user
        where cj.chatRoom.chatRoomId = :roomId
        """)
    List<ChatJoin> findChatJoinIdListByChatRoomId(Long roomId);

    @Query("""
        SELECT cr
        FROM ChatRoom cr
        WHERE cr.chatRoomId IN (
            SELECT cj2.chatRoom.chatRoomId
            FROM ChatJoin cj2
            WHERE cj2.user.userId = :hostUserId
        )
        AND cr.chatRoomId IN (
            SELECT cj3.chatRoom.chatRoomId
            FROM ChatJoin cj3
            WHERE cj3.user.userId = :inviteUserId
        )
        """)
    List<ChatRoom> findChatJoinsByUserIds(Long hostUserId, Long inviteUserId);
}
