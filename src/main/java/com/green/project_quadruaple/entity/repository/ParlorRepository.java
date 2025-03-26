package com.green.project_quadruaple.entity.repository;

import com.green.project_quadruaple.entity.model.Menu;
import com.green.project_quadruaple.entity.model.Parlor;
import com.green.project_quadruaple.entity.model.StayTourRestaurFest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ParlorRepository extends JpaRepository<Parlor, Long> {
    void deleteAllByMenuIn(List<Menu> menus);
    void deleteAllByMenuStayTourRestaurFest(StayTourRestaurFest strf);

    @Query("""
        select p from Parlor p
        join fetch p.menu
        """)
    List<Parlor> findAllWithFetchJoin();

    @Modifying
    @Query("DELETE FROM Parlor a WHERE a.menuId = :menuId ")
    int deleteByMenuId(Long menuId);

//    @Query("SELECT a FROM Menu a WHERE a.menuId = :menuId ")
    boolean existsByMenu_MenuId(Long menuId);
}

