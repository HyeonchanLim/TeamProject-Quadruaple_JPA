package com.green.project_quadruaple.busi;

import com.green.project_quadruaple.entity.model.BusinessNum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusiRepository extends JpaRepository<BusinessNum , Long> {
}
