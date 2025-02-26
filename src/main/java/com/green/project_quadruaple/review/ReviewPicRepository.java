package com.green.project_quadruaple.review;

import com.green.project_quadruaple.entity.model.ReviewPic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewPicRepository extends JpaRepository<ReviewPic , Long> {
}
