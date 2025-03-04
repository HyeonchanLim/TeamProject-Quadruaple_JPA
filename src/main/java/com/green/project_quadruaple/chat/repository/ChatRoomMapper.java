package com.green.project_quadruaple.chat.repository;

import com.green.project_quadruaple.chat.model.dto.ChatRoomDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatRoomMapper {

    List<ChatRoomDto> selChatRoomList(Long signedUserId, String role, int page);
}
