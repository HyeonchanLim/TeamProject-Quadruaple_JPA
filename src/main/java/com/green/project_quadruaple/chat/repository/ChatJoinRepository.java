package com.green.project_quadruaple.chat.repository;

import com.green.project_quadruaple.entity.model.ChatJoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChatJoinRepository extends JpaRepository<ChatJoin, Long> {

    @Query("""
            select cj from ChatJoin cj
            where cj.chatRoom.chatRoomId = :chatRoomId
                and cj.user.userId = :userId
            """)
    ChatJoin findByChatRoomIdAndUserId(Long chatRoomId, Long userId);
}
