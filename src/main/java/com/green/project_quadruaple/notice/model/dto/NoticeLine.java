package com.green.project_quadruaple.notice.model.dto;

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

    @ConstructorProperties({"noticeId", "category", "content", "opened", "createdAt"})
    public NoticeLine(Long noticeId, String category, String content, boolean opened, LocalDateTime createdAt) {
        final int txtLength = 12;
        StringBuilder sb=new StringBuilder("[");
        if(content.length()>txtLength){
            content=content.substring(0,txtLength);
            sb.append(NoticeCategory.getNameByValue(category)).append("] ").append(content).append("...");
        } else {
            sb.append(NoticeCategory.getNameByValue(category)).append("] ").append(content);
        }
        this.noticeId = noticeId;
        this.opened = opened;
        this.txt = sb.toString();
        this.noticedAt = createdAt.toLocalDate().isEqual(LocalDate.now())?
                createdAt.toLocalTime().toString():createdAt.toLocalDate().toString();
    }
}
