package com.green.project_quadruaple.user.model;

import com.green.project_quadruaple.entity.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findByUserUserId(Long userId);
}
