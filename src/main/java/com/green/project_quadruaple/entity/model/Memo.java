package com.green.project_quadruaple.entity.model;

import com.green.project_quadruaple.entity.base.UpdatedAt;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Memo  {

    @Id
    private Long memoId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "schedule_memo_id")
    private ScheduleMemo scheduleMemo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_user_id", nullable = false)
    private TripUser tripUser;

    private String content;

    @Column(name = "updated_at")
    @CreationTimestamp
    @Setter
    private LocalDateTime updatedAt;
}
