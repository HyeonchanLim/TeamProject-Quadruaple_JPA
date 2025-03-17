package com.green.project_quadruaple.notice;

import com.green.project_quadruaple.notice.model.req.NoticeAdminSendReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
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

//    //알람 여부 확인
//    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    @Operation(summary = "알람 여부 확인", description = "postman이나 swagger에서 확인안됨. 브라우저 네트워크 확인바람")
//    public SseEmitter subscribeSSE() {
//        return noticeService.subscribe();
//    }
//
//    //알람 테스트 insert
//    @PostMapping("test")
//    @Operation(summary = "테스트용 insert")
//    public void testInsNotice(@ParameterObject Long userId){
//        noticeService.testInsNotice(userId);
//    }
//
//    @GetMapping("check")
//    @Operation(summary = "알람리스트확인하기")
//    public ResponseEntity<?> checkNotice(@RequestParam("start_idx") int startIdx){
//        return noticeService.noticeCheck(startIdx);
//    }
//
//    @GetMapping("check-one")
//    @Operation(summary = "알람상세확인하기")
//    public ResponseEntity<?> checkNoticeOne(@RequestParam("notice_id") long noticeId){
//        return noticeService.checkNoticeOne(noticeId);
//    }
//
//    @PutMapping("readAll")
//    @Operation(summary = "알람 일괄 읽음처리")
//    public ResponseEntity<?> readAllNotice(){
//        return noticeService.readAllNotice();
//    }
//
//    @PostMapping("admin")
//    @Operation(summary = "관리자 service 알림 추가", description = "role은 유저 or 사업자 or Null 값 있으면 걔네한테만. null이면 전체")
//    public void noticeAdmin(@RequestBody NoticeAdminSendReq p){
//        noticeService.noticeAdmin(p);
//    }
}
