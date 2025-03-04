package com.green.project_quadruaple.notice;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("notice")
@Tag(name = "알림")
public class NoticeController {
    private final NoticeService noticeService;

    //알람 여부 확인
    @GetMapping
    @Operation(summary = "알람 여부 확인", description = "postman이나 swagger에서 확인안됨. 브라우저 네트워크 확인바람\n 30초에 한번 통신")
    public SseEmitter noticeCnt() {
        return noticeService.noticeCnt();
    }

    //알람 테스트 insert
    @PostMapping("test")
    @Operation(summary = "테스트용 insert")
    public void testInsNotice(){
        noticeService.testInsNotice();
    }

    //알람 리스트 확인
    @GetMapping("check")
    @Operation(summary = "알람리스트확인하기")
    public ResponseEntity<?> checkNotice(@RequestParam("start_idx") int startIdx){
        return noticeService.noticeCheck(startIdx);
    }

    @GetMapping("check-one")
    @Operation(summary = "알람상세확인하기")
    public ResponseEntity<?> checkNoticeOne(@RequestParam("notice_id") long noticeId){
        return noticeService.checkNoticeOne(noticeId);
    }

    @PutMapping
    @Operation(summary = "알람 일괄 읽음처리")
    public ResponseEntity<?> readAllNotice(){
        return noticeService.readAllNotice();
    }
}
