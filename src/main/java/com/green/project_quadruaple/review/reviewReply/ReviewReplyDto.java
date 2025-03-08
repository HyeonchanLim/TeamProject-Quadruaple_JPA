package com.green.project_quadruaple.review.reviewReply;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewReplyDto {
    private Long replyId;
    private Long reviewId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long reviewReplyId;
}
