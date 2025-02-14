package com.green.project_quadruaple.tripreview.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TripReviewScrapDto {
    private long tripId;
    private long tripReviewId;
}
