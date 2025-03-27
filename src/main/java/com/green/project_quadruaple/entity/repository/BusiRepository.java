package com.green.project_quadruaple.entity.repository;

import com.green.project_quadruaple.entity.model.BusinessNum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusiRepository extends JpaRepository<BusinessNum , String> {
}
