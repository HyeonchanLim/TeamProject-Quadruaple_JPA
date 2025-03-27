package com.green.project_quadruaple.entity.repository;

import com.green.project_quadruaple.entity.model.ReviewPic;
import com.green.project_quadruaple.entity.model.ReviewPicId;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewPicRepository extends JpaRepository<ReviewPic , ReviewPicId> {

    @Query("SELECT rp.id.title FROM ReviewPic rp WHERE rp.review.reviewId = :reviewId")
    List<String> findPicNamesByReviewId(@Param("review_id") Long reviewId);

    @Modifying
    @Query("DELETE FROM ReviewPic rp WHERE rp.review.reviewId = :reviewId")
    int deleteByReviewId(@Param("review_id") Long reviewId);

}
