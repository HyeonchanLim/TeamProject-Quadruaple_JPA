package com.green.project_quadruaple.entity.model;

import com.green.project_quadruaple.entity.base.CreatedAt;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeReceive extends CreatedAt {
    @EmbeddedId
    private NoticeReceiveId id;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("noticeId")
    @JoinColumn(name = "notice_id", nullable = false)
    private Notice notice;
    @Column(columnDefinition = "TINYINT(4) DEFAULT 0",nullable = false)
    private int open;
    @Column(columnDefinition = "TINYINT(4) DEFAULT 0",nullable = false)
    private int disable;
}
