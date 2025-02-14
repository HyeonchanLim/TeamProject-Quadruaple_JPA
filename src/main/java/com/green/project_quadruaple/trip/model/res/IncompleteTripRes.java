package com.green.project_quadruaple.trip.model.res;

import com.green.project_quadruaple.trip.model.dto.IncompleteTripDto;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class IncompleteTripRes {
    private List<IncompleteTripDto> matchTripId;
    private List<IncompleteTripDto> noMatchTripId;
}
