package com.green.project_quadruaple.entity.model;

import com.green.project_quadruaple.entity.base.CreatedAt;
import com.green.project_quadruaple.trip.model.ScheMemoType;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sche_memo")
public class ScheduleMemo extends CreatedAt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_memo_id")
    private Long scheduleMemoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;

    @Column(nullable = false)
    private int day;

    @Column(nullable = false)
    private int seq;

    @Column(name = "category", nullable = false)
    @Enumerated(EnumType.STRING)
    private ScheMemoType scheMemoType;
}
