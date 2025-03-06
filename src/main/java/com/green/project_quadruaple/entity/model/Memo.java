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
    @Column(name = "memo_id",nullable = false)
    private Long memoId;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "memo_id")
    private ScheduleMemo scheduleMemo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_user_id", nullable = false)
    private TripUser tripUser;

    @Column(length = 1000,nullable = false)
    private String content;

    @Column(name = "updated_at")
    @CreationTimestamp
    @Setter
    private LocalDateTime updatedAt;
}
