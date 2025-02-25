package com.green.project_quadruaple.chat.repository;

import com.green.project_quadruaple.entity.model.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
}
