package com.green.project_quadruaple.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@EqualsAndHashCode
public class Recent {
    @EmbeddedId
    private RecentId id;

    @JoinColumn(name = "user_id")
    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY)
    private User userId;

    @JoinColumn(name = "strf_id")
    @MapsId("strfId") // 복합키 사용 - Embedded 로 설정해놔서 JoinColumn 충돌 발생 -> MapsId 로 해결 + fk 관리
    @ManyToOne(fetch = FetchType.LAZY)
    private StayTourRestaurFest strfId;

    @Column(name = "inquired_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime inquiredAt;

    @Column(name = "undo_recent", nullable = false, columnDefinition = "TINYINT(4) DEFAULT 0")
    private int undoRecent;
}