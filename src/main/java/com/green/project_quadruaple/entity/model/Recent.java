package com.green.project_quadruaple.entity.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Recent {

    @Id
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User userId;

    @JoinColumn(name = "strf_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private StayTourRestaurFest strfId;

    @Column(name = "inquired_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime inquiredAt;

    @Column(name = "undo_recent", nullable = false, columnDefinition = "TINYINT(4) DEFAULT 0")
    private int undoRecent;

}