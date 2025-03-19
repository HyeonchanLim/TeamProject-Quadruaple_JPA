package com.green.project_quadruaple.trip.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TripLocationDto {
    private int locationId;
    private String locationPic;
    private String title;
}