package com.green.project_quadruaple.tripreview.model;

import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class TripReviewGetResponse {
    private List<TripReviewGetDto> reviews;
    private boolean hasMore;
}
