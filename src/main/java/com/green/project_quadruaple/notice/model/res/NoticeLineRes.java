package com.green.project_quadruaple.notice.model.res;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class NoticeLineRes {
    List<NoticeLine> noticeLines;
    boolean isMore;
}
