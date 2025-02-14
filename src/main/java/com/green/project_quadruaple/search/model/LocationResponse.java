package com.green.project_quadruaple.search.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LocationResponse {
    private int locationId;    // location_id
    private String locationTitle;      // title
    private String locationPic; // location_pic
}
