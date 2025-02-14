package com.green.project_quadruaple.tripreview.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TripReviewPatchDto {
    private long tripReviewId;
    @JsonIgnore
    private long userId;
    private String title;
    private String content;
}
