package com.green.project_quadruaple.review;

import com.green.project_quadruaple.entity.model.Review;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review , Long> {
    @Query("SELECT a.content , a.rating , a.stayTourRestaurFest.strfId FROM Review a WHERE a.user.userId = :userId")
    Review postRating (@Param("userId")Long userId);

    Review save(Review review);

    @Query("SELECT r.user.userId FROM ReviewReply rr " +
            "JOIN Review r ON rr.review.reviewId = r.reviewId " +
            "WHERE rr.replyId = :replyId")
    Optional<Long> findUserIdByReplyId(@Param("replyId") Long replyId);

    @Query("SELECT r.user FROM Review r WHERE r.reviewId = :reviewId")
    Optional<User> findUserByReviewId(@Param("reviewId") Long reviewId);

    @Modifying
    @Query("DELETE FROM Review r WHERE r.reviewId = :reviewId AND r.user.userId = :userId")
    int deleteByReviewIdAndUserId(@Param("reviewId") Long reviewId, @Param("userId") Long userId);
}

