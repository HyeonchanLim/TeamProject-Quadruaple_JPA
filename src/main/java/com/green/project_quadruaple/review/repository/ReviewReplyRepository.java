package com.green.project_quadruaple.review.repository;

import com.green.project_quadruaple.entity.model.ReviewReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewReplyRepository extends JpaRepository<ReviewReply, Long> {
}
