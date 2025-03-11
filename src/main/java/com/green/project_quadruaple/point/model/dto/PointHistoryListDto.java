package com.green.project_quadruaple.point.model.dto;

import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class PointHistoryListDto {
    private long pointHistoryId;
    private String usedAt;
    private int category;
    private LocalDateTime addedAt;
    private int amount;
    private int remainPoint;
}
