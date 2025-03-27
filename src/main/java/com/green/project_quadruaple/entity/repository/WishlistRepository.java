package com.green.project_quadruaple.entity.repository;

import com.green.project_quadruaple.entity.model.StayTourRestaurFest;
import com.green.project_quadruaple.entity.model.User;
import com.green.project_quadruaple.entity.model.Wishlist;
import com.green.project_quadruaple.entity.ids.WishlistId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WishlistRepository extends JpaRepository<Wishlist , WishlistId> {
    Optional<Wishlist> findByUserIdAndStrfId(User userId, StayTourRestaurFest strfId);
}
