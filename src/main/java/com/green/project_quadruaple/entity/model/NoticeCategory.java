package com.green.project_quadruaple.entity.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoticeCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_category_id",nullable = false)
    private Long noticeCategoryId;
    @Column(nullable = false,length = 10)
    private String category;
}
