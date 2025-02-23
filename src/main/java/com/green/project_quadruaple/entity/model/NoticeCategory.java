package com.green.project_quadruaple.entity.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticeCategoryId;
    @Column(nullable = false)
    private String category;
}
