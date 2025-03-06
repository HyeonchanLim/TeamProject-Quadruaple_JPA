package com.green.project_quadruaple.review.reviewReply;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewReplyDto {
    private Long reviewId;
    private String content;
    private String createdAt;
}
