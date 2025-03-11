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
    private String category;
    private String txt;
    private boolean opened;
    private String noticedAt;

    @ConstructorProperties({"noticeId", "category", "title", "opened", "createdAt"})
    public NoticeLine(Long noticeId, String category, String title, boolean opened, LocalDateTime createdAt) {
        this.noticeId = noticeId;
        this.category = NoticeCategory.getNameByValue(category);
        this.opened = opened;
        this.txt = title;
        this.noticedAt = createdAt.toLocalDate().isEqual(LocalDate.now())?
                createdAt.toLocalTime().toString():createdAt.toLocalDate().toString();
    }
}
