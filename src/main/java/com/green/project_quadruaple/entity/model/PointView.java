package com.green.project_quadruaple.entity.model;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;
import java.time.LocalDateTime;

@Getter
@Entity
@Immutable
@Subselect("SELECT * FROM point_view")
public class PointView {

    @Id
    @Column(name = "point_history_id")
    private Long pointHistoryId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false)
    private int category;

    @Column(name = "strf_id")
    private Long strfId;

    @Column(length = 255)
    private String title;

    @Column(name = "point_card_id")
    private Long pointCardId;

    @Column(name = "canceled_point_history")
    private Long canceledPointHistory;

    @Column(length = 30)
    private String tid;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column
    private Integer amount;

    @Column(name = "remain_point", nullable = false)
    private int remainPoint;
}