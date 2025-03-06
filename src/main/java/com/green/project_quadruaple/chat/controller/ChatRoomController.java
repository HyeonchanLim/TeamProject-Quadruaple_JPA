package com.green.project_quadruaple.chat.controller;

import com.green.project_quadruaple.chat.model.dto.ChatDto;
import com.green.project_quadruaple.chat.model.dto.ChatRoomDto;
import com.green.project_quadruaple.chat.model.req.GetChatRoomReq;
import com.green.project_quadruaple.chat.service.ChatRoomService;
import com.green.project_quadruaple.chat.model.req.PostChatRoomReq;
import com.green.project_quadruaple.common.config.jwt.UserRole;
import com.green.project_quadruaple.common.model.ResponseWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("chat-room")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @PostMapping
    @Operation(summary = "채팅방 생성")
    public ResponseWrapper<Long> postChatRoom(@RequestBody PostChatRoomReq req) {
        return chatRoomService.createChatRoom(req);
    }

    @GetMapping("{room_id}")
    @Operation(summary = "채팅 30개씩 불러오기")
    public ResponseWrapper<List<ChatDto>> getChatList(@Parameter @PathVariable("room_id") Long roomId,
                                                      @Parameter @RequestParam int page) {
        return chatRoomService.getChatList(roomId, page);
    }

    @GetMapping()
    @Operation(summary = "내 채팅방 목록 30개씩 불러오기")
    public ResponseWrapper<List<ChatRoomDto>> getChatRoomList(@Parameter @RequestParam int page,
                                                              @Parameter @RequestParam String role) {
        return chatRoomService.getChatRoomList(page, role);
    }
}
