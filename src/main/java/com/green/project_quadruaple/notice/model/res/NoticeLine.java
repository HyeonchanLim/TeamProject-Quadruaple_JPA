package com.green.project_quadruaple.notice.model.res;

import com.green.project_quadruaple.entity.base.NoticeCategory;
import lombok.*;


import java.beans.ConstructorProperties;
import java.time.LocalDate;
import java.time.LocalDateTime;
@ToString
@Getter
@EqualsAndHashCode
public class NoticeLine {
    private Long noticeId;
    private String txt;
    private boolean opened;
    private String noticedAt;

    @ConstructorProperties({"noticeId", "category", "title", "opened", "createdAt"})
    public NoticeLine(Long noticeId, String category, String title, boolean opened, LocalDateTime createdAt) {
        StringBuilder sb=new StringBuilder("[");
        sb.append(NoticeCategory.getNameByValue(category)).append("] ").append(title);
        this.noticeId = noticeId;
        this.opened = opened;
        this.txt = sb.toString();
        this.noticedAt = createdAt.toLocalDate().isEqual(LocalDate.now())?
                createdAt.toLocalTime().toString():createdAt.toLocalDate().toString();
    }
}
