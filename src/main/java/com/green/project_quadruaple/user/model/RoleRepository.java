package com.green.project_quadruaple.user.model;

import com.green.project_quadruaple.common.config.jwt.UserRole;
import com.green.project_quadruaple.entity.model.Role;
import com.green.project_quadruaple.entity.model.RoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, RoleId> {

    List<Role> findByUserUserId(Long userId);

    @Query("""
            select r from Role r
            join fetch r.user
            where r.user.userId = :userId
                and r.role = :role
            """)
    Role findByUserIdAndRoleName(Long userId, UserRole role);

    @Query("""
            select r from Role r
            join r.user u
            join BusinessNum bn
                on bn.user.userId = u.userId
            join StayTourRestaurFest strf
                on strf.busiNum.busiNum = bn.busiNum
            where strf.strfId = :strfId
                and r.role = :role
            """)
    Role findByStrfIdAndRoleName(Long strfId, UserRole role);

    Role findByRole(UserRole role);
}
