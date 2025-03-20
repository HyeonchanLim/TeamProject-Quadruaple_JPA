package com.green.project_quadruaple.notice.model.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NoticeLineRes {
    private List<NoticeLine> noticeLines;
    //private Boolean isMore;
    private int unreadNoticeCnt;
    private int noticeCnt;
}
