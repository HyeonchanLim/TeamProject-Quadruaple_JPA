package com.green.project_quadruaple.trip.model.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class LocationDto {

    private long locationId;
    private String locationPic;
    private String title;
}
