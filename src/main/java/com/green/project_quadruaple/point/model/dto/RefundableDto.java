package com.green.project_quadruaple.point.model.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class RefundableDto {
    private Long pointHistoryId;
    private int amount;
    private LocalDateTime purchaseAt;
    private boolean refundable;
}
