package com.green.project_quadruaple.review;

import com.green.project_quadruaple.entity.model.Review;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review , Long> {
    @Query("SELECT a.content , a.rating , a.stayTourRestaurFest.strfId FROM Review a WHERE a.user.userId = :userId")
    Review postRating (@Param("userId")Long userId);

    Review save(Review review);

    //void deleteReviewBy(Long reviewId);
}
