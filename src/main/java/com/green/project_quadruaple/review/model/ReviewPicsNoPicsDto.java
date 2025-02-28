package com.green.project_quadruaple.review.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReviewPicsNoPicsDto {
    private long reviewId;
    private String title;
    private List<String> pics;

}
