package com.green.project_quadruaple.review.repository;

import com.green.project_quadruaple.entity.model.Review;
import com.green.project_quadruaple.entity.model.ReviewReply;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewReplyRepository extends JpaRepository<ReviewReply, Long> {
    @Query("SELECT rr FROM ReviewReply rr WHERE rr.review.reviewId = :reviewId ORDER BY rr.updatedAt DESC, rr.createdAt DESC")
    List<ReviewReply> findLatestReviewReplyByReviewId(@Param("reviewId") Long reviewId, Pageable pageable);

    @Query("SELECT r FROM ReviewReply r WHERE r.review.reviewId = :reviewId ORDER BY r.createdAt DESC")
    List<ReviewReply> findLatestReplyByReviewId(@Param("reviewId") Long reviewId, Pageable pageable);





}
