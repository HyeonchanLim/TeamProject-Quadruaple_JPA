package com.green.project_quadruaple.review.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessDto {
    private Long strfId;
    private String strfTitle;
    private Integer state;
    private Integer reviewCnt;
    private Double ratingAvg;
    private String content;
    private Integer rating;
    private Long userId;
    private String userName;
    private String writerUserProfilePic;
    private Integer providerType;
    private String reviewReply;
    private String reviewPicList;
}
