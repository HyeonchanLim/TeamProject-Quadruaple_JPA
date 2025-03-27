package com.green.project_quadruaple.entity.repository;

import com.green.project_quadruaple.entity.model.Menu;
import com.green.project_quadruaple.entity.model.Room;
import com.green.project_quadruaple.entity.model.StayTourRestaurFest;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

    void deleteAllByMenuIn(List<Menu> menus);
    void deleteAllByMenuStayTourRestaurFest(StayTourRestaurFest strf);

    @Query("""
        select r from Room r
        group by r.menu
        """)
    List<Room> findAllRoomsWithDistinctMenu();

    Optional<Room> findById(Long id);

    @Query("SELECT a FROM Room a WHERE a.menu.menuId = :menuId ")
    void deleteByMenuId(@Param("menu_id") Long menuId);

    @Modifying
    @Query("DELETE FROM Room a WHERE a.menu.menuId = :menuId ")
    int deleteByRoomId(@Param("menu_id") Long menuId);
}
