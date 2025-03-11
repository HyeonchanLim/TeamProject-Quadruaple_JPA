package com.green.project_quadruaple.review;

import com.green.project_quadruaple.review.model.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReviewMapper {
    List<ReviewSelRes> getReviewWithPics(Long strfId,int startIdx , int size);
    List<MyReviewSelRes> getMyReviews(Long userId, int startIdx , int size);


    int postRating(ReviewPostReq req,Long userId);

    int postReviewPic(ReviewPicDto pics);

    int patchReview(ReviewUpdReq req);
    int patchReviewPic(@Param("pics") List<ReviewPicDto> reviewPicList);

    int deleteReview (Long reviewId,Long userId);
    int deleteReviewPic(Long reviewId);



    // 사업자 리뷰 조회
    String findUserRoleByUserId(Long userId);

    List<BusinessDto> selectBusinessReview(
            @Param("userId") Long userId,
            @Param("startIdx") int startIdx,
            @Param("pageSize") int pageSize,
            @Param("offset") int offset
    );

    List<ReviewPicDto> selectReviewPics(@Param("reviewId") Long reviewId);



}
