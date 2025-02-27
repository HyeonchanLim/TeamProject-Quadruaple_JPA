package com.green.project_quadruaple.review.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BusinessDto {
    private Long userId;
    private Long strfId;
    private String strfTitle;
    private int state;
    private int reviewCnt;
    private int wishCnt;
    private double ratingAvg;
    private String content;
    private int rating;
    private String writerUserProfilePic;
    private String userName;
    private int providerType;
    private String reviewReply;
    private List<String> reviewPicList;


    public BusinessDto(Long strfId, String strfTitle, int state, int reviewCnt, int wishCnt,
                       double ratingAvg, String content, int rating, Long userId, String userName,
                       String writerUserProfilePic, int providerType, String reviewReply, List<String> reviewPicList) {
        this.strfId = strfId;
        this.strfTitle = strfTitle;
        this.state = state;
        this.reviewCnt = reviewCnt;
        this.wishCnt = wishCnt;
        this.ratingAvg = ratingAvg;
        this.content = content;
        this.rating = rating;
        this.userId = userId;
        this.userName = userName;
        this.writerUserProfilePic = writerUserProfilePic;
        this.providerType = providerType;
        this.reviewReply = reviewReply;
        this.reviewPicList = reviewPicList;
    }
}
