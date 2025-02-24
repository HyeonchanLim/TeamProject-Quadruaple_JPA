package com.green.project_quadruaple.entity.model;

import com.green.project_quadruaple.entity.base.CreatedAt;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Report extends CreatedAt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long reportId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_user_id", nullable = false)
    private User reportUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id", nullable = false)
    private User manager;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private StayTourRestaurFest category;

    @Column(name = "report_target", nullable = false)
    private Long reportTarget;

    @Column(nullable = false, length = 800)
    private String reason;

    @Column(name = "processed", length = 800)
    private String processed;

    @Column(name = "processed_at")
    private LocalDateTime processedAt;

    public Report(User reportUser, User manager, StayTourRestaurFest category, Long reportTarget, String reason) {
        this.reportUser = reportUser;
        this.manager = manager;
        this.category = category;
        this.reportTarget = reportTarget;
        this.reason = reason;
    }
}
