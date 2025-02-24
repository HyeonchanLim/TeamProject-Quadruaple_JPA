package com.green.project_quadruaple.entity.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Recent {

    @Id
    @Column(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User userId;

    @Column(name = "strf_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private StayTourRestaurFest strfId;

    @Column(name = "inquired_at", nullable = false)
    private LocalDateTime inquiredAt;

    @Column(name = "undo_recent", nullable = false, columnDefinition = "TINYINT DEFAULT 0")
    private int undoRecent;

    public Recent(User userId, StayTourRestaurFest strfId) {
        this.userId = userId;
        this.strfId = strfId;
        this.inquiredAt = LocalDateTime.now(); // 현재 시간으로 기본값 설정
    }
}