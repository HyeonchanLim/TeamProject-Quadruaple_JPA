package com.green.project_quadruaple.trip.model.res;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class FindPathRes {

    private String pathType;
    private int totalTime;
    private int totalDistance;
    private int payment;
}
