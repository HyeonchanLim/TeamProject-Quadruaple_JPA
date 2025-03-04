package com.green.project_quadruaple.chat.repository;

import com.green.project_quadruaple.entity.model.ChatReceive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatReceiveRepository extends JpaRepository<ChatReceive, Long> {

    @Query("""
        select case when count(cr) > 0 then true else false
            end from ChatReceive cr
        where cr.listenerId.role.user.userId = :signedUserId
        """)
    boolean findByUserId(Long signedUserId);

    @Modifying
    @Query("""
        delete from ChatReceive cr
        where cr.listenerId.role.user.userId = :signedUserId
            and cr.chat.chatJoin.chatRoom.chatRoomId = :roomId
        """)
    void deleteByUserId(Long signedUserId, Long roomId);
}
