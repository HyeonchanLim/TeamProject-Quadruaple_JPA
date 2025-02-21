package com.green.project_quadruaple.entity.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Schedule {

    @Id
    @Column(name = "schedule_id")
    private Long scheduleId;

    @MapsId
    @JoinColumn(name = "schedule_id")
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ScheMemo ScheMemo;

    private Double distance;

    private Integer duration;

    @Column(name = "pathtype", columnDefinition = "TINYINT")
    private Integer pathType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "strf_id")
    private Strf strf;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Schedule(ScheMemo scheMemo, Double distance, Integer duration, Integer pathType, Strf strf) {
        ScheMemo = scheMemo;
        this.distance = distance;
        this.duration = duration;
        this.pathType = pathType;
        this.strf = strf;
    }
}
