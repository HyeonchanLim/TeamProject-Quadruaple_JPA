package com.green.project_quadruaple.pointcard.model;

import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PointCardGetDto {
    private String pointCardId;
    private int available;
    private int discountPer;
    private int finalPayment;
}
