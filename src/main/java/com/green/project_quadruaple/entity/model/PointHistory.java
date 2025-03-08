package com.green.project_quadruaple.entity.model;

import com.green.project_quadruaple.entity.base.CreatedAt;
import com.green.project_quadruaple.entity.base.Refund;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PointHistory extends CreatedAt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_history_id",nullable = false)
    private Long pointHistoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, columnDefinition = "TINYINT(4) default 0")
    private int category;

    @Column(name = "related_id", nullable = false)
    private Long relatedId;

    @Column(length = 30)
    private String tid;

    @Column
    private Integer amount;

    @Column(name = "remain_point",nullable = false)
    private int remainPoint;

}
