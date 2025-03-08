package com.green.project_quadruaple.point.model.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@EqualsAndHashCode
public class PointListDto {
    private String usedAt;
    private int category;
    private LocalDateTime addedAt;
    private int amount;
    private int remainPoint;
}
