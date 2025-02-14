package com.green.project_quadruaple.tripreview.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class TripReviewPostRes {
    private long tripReviewId;
    private List<String> tripReviewPic;
}
