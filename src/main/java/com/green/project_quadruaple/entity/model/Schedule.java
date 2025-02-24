package com.green.project_quadruaple.entity.model;

import com.green.project_quadruaple.entity.base.UpdatedAt;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Schedule extends UpdatedAt {

    @Id
    @Column(name = "schedule_id")
    private Long scheduleId;

    @MapsId
    @JoinColumn(name = "schedule_id")
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ScheduleMemo ScheMemo;

    private Double distance;

    private Integer duration;

    @Column(name = "pathtype", columnDefinition = "TINYINT")
    private Integer pathType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "strf_id")
    private StayTourRestaurFest strf;

}
