package com.green.project_quadruaple.entity.model;

import com.green.project_quadruaple.trip.model.Category;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class ScheMemo {

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

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    public ScheMemo(Trip trip, int day, int seq, Category category) {
        this.trip = trip;
        this.day = day;
        this.seq = seq;
        this.category = category;
    }
}
