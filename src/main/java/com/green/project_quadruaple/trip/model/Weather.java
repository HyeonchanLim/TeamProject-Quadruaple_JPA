package com.green.project_quadruaple.trip.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public enum Weather {

    SUNNY("sunny", 1),
    CLOUDY("cloudy", 3),
    OVERCAST("overcast", 4),
    RAIN("rain", 11),
    SNOW("snow", 13);

    private final String name;
    private final int value;

    public static String getNameByValue(int value) {
        for (Weather weather : Weather.values()) {
            if (weather.getValue() == value) {
                return weather.getName();
            }
        }
        return null;
    }
}

