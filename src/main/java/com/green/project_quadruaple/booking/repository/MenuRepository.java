package com.green.project_quadruaple.booking.repository;

import com.green.project_quadruaple.entity.model.Menu;
import com.green.project_quadruaple.entity.model.StayTourRestaurFest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    void deleteByStayTourRestaurFest(StayTourRestaurFest strf);
}
