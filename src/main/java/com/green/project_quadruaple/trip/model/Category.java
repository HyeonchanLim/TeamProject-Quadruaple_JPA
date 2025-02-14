package com.green.project_quadruaple.trip.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {
    STAY("숙소", "STAY"),
    TOUR("관광지", "TOUR"),
    RESTAUR("맛집", "RESTAUR"),
    FEST("축제", "FEST");

    private final String name;
    private final String value;

    public static Category getKeyByName(String name) {
        for (Category category : Category.values()) {
            if (category.getName().equals(name)) {
                return category;
            }
        }
        return null;
    }
}
