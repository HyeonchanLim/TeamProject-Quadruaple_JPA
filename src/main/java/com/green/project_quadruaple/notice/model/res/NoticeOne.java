package com.green.project_quadruaple.notice.model.res;

import com.green.project_quadruaple.entity.base.NoticeCategory;
import com.green.project_quadruaple.entity.model.Notice;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@ToString
public class NoticeOne {
    private Long noticeId;
    private String title;
    private String content;
    private NoticeCategory category;
    private String noticedAt;
    private Long foreignNum;

    public NoticeOne(Notice no, LocalDateTime createdAt) {
        this.noticeId=no.getNoticeId();
        this.title=no.getTitle();
        this.content=no.getContent();
        this.category=no.getNoticeCategory();
        this.noticedAt = createdAt.toLocalDate().isEqual(LocalDate.now())?
                createdAt.toLocalTime().toString():createdAt.toLocalDate().toString();
        this.foreignNum=no.getForeignNum();
    }
}
