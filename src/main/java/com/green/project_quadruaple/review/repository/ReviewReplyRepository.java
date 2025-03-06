package com.green.project_quadruaple.review.repository;

import com.green.project_quadruaple.entity.model.Review;
import com.green.project_quadruaple.entity.model.ReviewReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewReplyRepository extends JpaRepository<ReviewReply, Long> {





}
