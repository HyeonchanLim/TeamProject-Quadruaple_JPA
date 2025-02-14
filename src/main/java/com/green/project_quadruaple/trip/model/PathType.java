package com.green.project_quadruaple.trip.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PathType {

    SUBWAY("SUBWAY","지하철", 1),
    CITY_BUS("CITY_BUS", "버스", 2),
    CITY_BUS_SUBWAY("CITY_BUS_SUBWAY", "버스 + 지하철", 3),
    TRAIN("TRAIN", "기차", 11),
    BUS("BUS", "시외버스", 12),
    AIR("AIR", "항공", 13),
    BUS_TRAIN("BUS_TRAIN", "시외교통 복합(기차 + 시외버스)", 20),
    WALK("WALK", "도보", 101);


    private final String code;
    private final String name;
    private final int value;

    public static PathType getKeyByName(String name) {
        for (PathType pathType : PathType.values()) {
            if (pathType.getName().equals(name)) {
                return pathType;
            }
        }
        return null;
    }

    public static PathType getKeyByValue(int value) {
        for (PathType pathType : PathType.values()) {
            if (pathType.getValue() == value) {
                return pathType;
            }
        }
        return null;
    }
}
