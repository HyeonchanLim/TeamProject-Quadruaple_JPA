package com.green.project_quadruaple.datamanager.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@EqualsAndHashCode
public class ReviewDto {
    private String content;
    private int rating;

}
