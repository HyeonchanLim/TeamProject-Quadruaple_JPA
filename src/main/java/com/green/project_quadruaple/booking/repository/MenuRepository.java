package com.green.project_quadruaple.booking.repository;

import com.green.project_quadruaple.entity.model.Menu;
import com.green.project_quadruaple.entity.model.StayTourRestaurFest;
import com.green.project_quadruaple.trip.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    void deleteByStayTourRestaurFest(StayTourRestaurFest strf);

    @Query("""
        select m from Menu m
        where m.stayTourRestaurFest.category = :category
        """)
    List<Menu> findByCategory(Category category);
}
