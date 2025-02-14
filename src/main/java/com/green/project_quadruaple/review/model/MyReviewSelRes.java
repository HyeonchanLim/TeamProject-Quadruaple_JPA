package com.green.project_quadruaple.review.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@Schema(title = "리뷰 정보")
@ToString
@EqualsAndHashCode
public class MyReviewSelRes {
    @Schema(description = "리뷰 ID")
    private Long reviewId;
    private Long writerUserId;
    @Schema(description = "STRF ID")
    private Long strfId;
    @Schema(description = "리뷰 내용")
    private String content;
    private String strfTitle;
    @Schema(description = "평점")
    private double rating;
    @Schema(description = "작성자 이름")
    private String writerUserName;
    @Schema(description = "작성자 프로필 사진")
    private String writerUserPic;
    @Schema(description = "사용자가 작성한 총 리뷰 개수")
    private int userWriteReviewCnt;
    @Schema(description = "리뷰 작성 날짜")
    private String reviewWriteDate;
    @Schema(description = "리뷰 사진 목록")
    private List<ReviewPic> myReviewPic;
    private boolean isMore;
}

