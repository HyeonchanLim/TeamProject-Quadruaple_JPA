package com.green.project_quadruaple.point.model.dto;

import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PointCardGetDto {
    private Long pointCardId;
    private int available;
    private int discountPer;
    private int finalPayment;
}
