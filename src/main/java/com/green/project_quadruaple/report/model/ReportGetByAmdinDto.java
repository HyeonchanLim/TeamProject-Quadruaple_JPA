package com.green.project_quadruaple.report.model;

import com.green.project_quadruaple.entity.base.ReportEnum;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportGetByAmdinDto {
    private long reportId;
    private long reportUserId;
    private ReportEnum category;
    private long reportTarget;
    private String reason;
    private LocalDateTime createdAt;
    private String processed;
    private LocalDateTime processedAt;
}
