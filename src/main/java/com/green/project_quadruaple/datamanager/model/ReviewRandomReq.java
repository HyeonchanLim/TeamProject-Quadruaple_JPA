package com.green.project_quadruaple.datamanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReviewRandomReq {
    @JsonIgnore
    private long reviewId;
    private String content;
    private int rating;
    private long strfId;
    private long userId;

}
