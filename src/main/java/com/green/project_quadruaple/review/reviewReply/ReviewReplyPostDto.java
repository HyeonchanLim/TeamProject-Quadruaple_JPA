package com.green.project_quadruaple.review.reviewReply;

import com.green.project_quadruaple.entity.model.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewReplyPostDto {
    private Long reviewId;
    private String content;



}
