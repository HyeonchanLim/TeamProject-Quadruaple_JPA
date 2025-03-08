package com.green.project_quadruaple.user.model;

import com.green.project_quadruaple.common.config.jwt.UserRole;
import com.green.project_quadruaple.entity.model.Role;
import com.green.project_quadruaple.entity.model.RoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, RoleId> {

    List<Role> findByUserUserId(Long userId);

    Role findByRole(UserRole role);
}
