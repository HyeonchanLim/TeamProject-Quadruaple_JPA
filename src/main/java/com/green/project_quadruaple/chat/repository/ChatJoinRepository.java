package com.green.project_quadruaple.chat.repository;

import com.green.project_quadruaple.entity.model.ChatJoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChatJoinRepository extends JpaRepository<ChatJoin, Long> {

    @Query("""
            select cj from ChatJoin cj
            where cj.chatRoom.chatRoomId = :chatRoomId
                and cj.role.user.userId = :userId
            """)
    ChatJoin findByChatRoomIdAndUserId(Long chatRoomId, Long userId);

    @Query("""
            select count(cj) from ChatJoin cj
            where cj.chatRoom.chatRoomId = :roomId
                and cj.role.user.userId = :signedUserId
            """)
    Integer existsJoinUser(Long roomId, Long signedUserId);
}
