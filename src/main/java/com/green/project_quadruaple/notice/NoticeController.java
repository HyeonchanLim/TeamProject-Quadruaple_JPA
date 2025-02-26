package com.green.project_quadruaple.notice;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("notice")
@Tag(name = "알림")
public class NoticeController {
    private final NoticeService noticeService;

    @GetMapping
    public SseEmitter noticeCnt (){
        return noticeService.noticeCnt();
    }

    @PostMapping("test")
    public void testInsNotice(){
        noticeService.testInsNotice();
    }
}
