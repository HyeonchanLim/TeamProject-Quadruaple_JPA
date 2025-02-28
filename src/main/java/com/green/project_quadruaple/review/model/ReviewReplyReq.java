package com.green.project_quadruaple.review.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewReplyReq {
    private Long strfId;
    private Long userId;
    private Long reviewId;
    private String content;
}
