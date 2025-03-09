package com.green.project_quadruaple.booking.model.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class DiscountPerDto {

    private final Long receiveId;
    private final int discountPer;
}
