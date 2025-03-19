package com.green.project_quadruaple.strf;

import com.green.project_quadruaple.entity.model.Amenity;
import jakarta.transaction.Transactional;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AmenityRepository extends JpaRepository<Amenity , Long> {


}
