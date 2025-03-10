package com.green.project_quadruaple.booking.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RefundRate {

    TWO_DAYS_AGO(50)
    , SIX_DAYS_AGO(70);

    private final int percent;
}
