package com.green.project_quadruaple.review.model;

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
    private Long strfId;
    private String strfTitle;
    private Integer state;
    private Integer reviewCnt;
    private Integer wishCnt;
    private Double ratingAvg;
    private String content;
    private Integer rating;
    private Long userId;
    private String userName;
    private String writerUserProfilePic;
    private Integer providerType;
    private String reviewReply;
    private List<ReviewPicDto> reviewPicList;   // 추가된 필드

}
