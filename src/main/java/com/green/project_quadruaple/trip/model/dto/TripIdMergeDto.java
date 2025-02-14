package com.green.project_quadruaple.trip.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TripIdMergeDto {

    private long tripId;

    List<IncompleteTripDto> incompleteTripList;
}
