package com.green.project_quadruaple.trip.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class IncompleteTripDto {

    private long tripId;
    private String title;
    private String startAt;
    private String endAt;
    private String locationPic;
    private long totalDay;

    @JsonIgnore
    private long locationId;

}
