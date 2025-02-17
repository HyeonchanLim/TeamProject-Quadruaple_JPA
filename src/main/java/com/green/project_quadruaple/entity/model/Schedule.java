package com.green.project_quadruaple.entity.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Schedule {

    @Id
    @Column(name = "schedule_id")
    private Long scheduleId;

    @MapsId
    @JoinColumn(name = "schedule_id")
    @OneToOne(cascade = CascadeType.REMOVE)
    private ScheMemo ScheMemo;

    private Integer distance;

    private Integer duration;

    private Integer pathType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "strf_id")
    private Strf strf;

    private LocalDateTime updatedAt;
}
