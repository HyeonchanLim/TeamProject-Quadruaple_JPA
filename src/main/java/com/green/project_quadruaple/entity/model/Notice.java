package com.green.project_quadruaple.entity.model;

import com.green.project_quadruaple.entity.base.CreatedAt;
import com.green.project_quadruaple.entity.base.NoticeCategory;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notice extends CreatedAt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticeId;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false,length = 800)
    private String content;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private NoticeCategory noticeCategory;

    @Column
    private Long foreignNum;
}
