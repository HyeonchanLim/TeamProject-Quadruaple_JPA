package com.green.project_quadruaple.review.model;

import com.green.project_quadruaple.review.reviewReply.ReviewReplyDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BusinessDto {
    private String createdAt;
    private Long reviewId;
    private Long strfId;
    private String strfTitle;
    private Integer state;
    private Integer reviewCnt;
    private Integer wishCnt;
    private Double ratingAvg;
    private String content;
    private Integer rating;
    private Integer userId;
    private String userName;
    private String writerUserProfilePic;
    private Integer providerType;
    private String reviewReply;
    private String reviewReplyCreatedAt;
    private List<ReviewPicDto> reviewPicList;
    private Long reviewReplyId;

    private Boolean isMore;


}
