package com.green.project_quadruaple.booking.repository;

import com.green.project_quadruaple.entity.model.Menu;
import com.green.project_quadruaple.entity.model.Room;
import com.green.project_quadruaple.entity.model.StayTourRestaurFest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("SELECT m FROM Room m where m.menu.menuId = :menuId")
    List<Room> findByMenuId(Long menuId);

    void deleteAllByMenuIn(List<Menu> menus);
    void deleteAllByMenuStayTourRestaurFest(StayTourRestaurFest strf);
}
