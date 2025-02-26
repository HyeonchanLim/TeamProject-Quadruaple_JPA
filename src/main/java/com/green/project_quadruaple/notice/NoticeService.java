package com.green.project_quadruaple.notice;

import com.green.project_quadruaple.common.config.security.AuthenticationFacade;
import com.green.project_quadruaple.common.model.ResponseWrapper;
import com.green.project_quadruaple.entity.base.NoticeCategory;
import com.green.project_quadruaple.entity.model.Notice;
import com.green.project_quadruaple.entity.model.NoticeReceive;
import com.green.project_quadruaple.entity.model.NoticeReceiveId;
import com.green.project_quadruaple.entity.model.User;
import com.green.project_quadruaple.expense.model.dto.ExpenseDto;
import com.green.project_quadruaple.user.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeReceiveRepository noticeReceiveRepository;
    private final NoticeRepository noticeRepository;
    private final AuthenticationFacade authenticationFacade;
    private final UserRepository userRepository;

    // SSE 연결을 관리하는 저장소 (여러 유저 지원 가능)
    private final ConcurrentHashMap<Long, CopyOnWriteArrayList<SseEmitter>> emitters = new ConcurrentHashMap<>();

    //미열람 알람 개수를 실시간으로 전송하는 SSE
    public SseEmitter noticeCnt() {
        long userId=116L;
        //long userId = authenticationFacade.getSignedUserId();
        SseEmitter sseEmitter = new SseEmitter(30 * 1000L); // 60초 타임아웃 설정
        // 유저별 SseEmitter 리스트 저장
        emitters.computeIfAbsent(userId, k -> new CopyOnWriteArrayList<>()).add(sseEmitter);
        // 연결 종료 시 리스트에서 제거
        sseEmitter.onCompletion(() -> removeEmitter(userId, sseEmitter));
        sseEmitter.onTimeout(() -> removeEmitter(userId, sseEmitter));
        // 현재 미열람 알람 개수를 즉시 전송
        sendNoticeCount(userId);
        return sseEmitter;
    }

    // 30초마다 알림 갯수 전송
    public void sendNoticeCount(long userId) {
        long noticeCnt = noticeReceiveRepository.countUnreadNoticesByUserId(userId);
        if (emitters.containsKey(userId)) {
            for (SseEmitter emitter : emitters.get(userId)) {
                try {
                    emitter.send(SseEmitter.event()
                            .name("notice-count")
                            .data(noticeCnt));
                } catch (IOException e) {
                    removeEmitter(userId, emitter);
                }
            }
        }
    }
    //SSE 연결 제거
    private void removeEmitter(long userId, SseEmitter emitter) {
        if (emitters.containsKey(userId)) {
            emitters.get(userId).remove(emitter);
        }
    }

    public void testInsNotice (){
        Notice notice = new Notice();
        notice.setContent("테스트 알람입니다.");
        notice.setNoticeCategory(NoticeCategory.SERVICE);
        noticeRepository.save(notice);
        NoticeReceiveId noticeReceiveId = new NoticeReceiveId(116L, notice.getNoticeId());
        NoticeReceive noticeReceive = NoticeReceive.builder()
                .id(noticeReceiveId)
                .notice(notice)
                .user(userRepository.findById(116L).orElseThrow(() -> new RuntimeException("User not found")))
                .open(false)
                .disable(false)
                .build();
        noticeReceiveRepository.save(noticeReceive);
    }
}
