package com.green.project_quadruaple.chat.repository;

import com.green.project_quadruaple.chat.model.dto.ChatDto;
import com.green.project_quadruaple.entity.model.Chat;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChatRepository extends JpaRepository<Chat, Long> {


}
