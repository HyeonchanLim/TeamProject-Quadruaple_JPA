package com.green.project_quadruaple.search.model.filter;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class StaySearchRes {
    private boolean isMore;
    private List<SearchAmenity> amenities;
    private List<SearchStay> stays;

}
