package com.green.project_quadruaple.user.model;

import com.green.project_quadruaple.entity.model.Role;
import com.green.project_quadruaple.entity.model.RoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, RoleId> {
    List<Role> findByUserUserId(Long userId);

    @Query("""
            select r from Role r
            where r.user.userId = :userId
                and r.role = :roldName
            """)
    Role findByUserIdAndRoleName(Long userId, String roleName);

    @Query("""
            select r from Role r
            join r.user u
            join BusinessNum b
                on b.user.userId = u.userId
            join StayTourRestaurFest strf
                on strf.busiNum.busiNum = b.busiNum
                and strf.strfId = :strfId
            where r.role = :roleName
            """)
    Role findByStrfIdAndRoleName(Long strfId, String roleName);
}
