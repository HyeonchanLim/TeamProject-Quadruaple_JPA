package com.green.project_quadruaple.entity.repository;

import com.green.project_quadruaple.entity.model.Memo;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemoRepository extends JpaRepository<Memo , Long> {
    @Query("SELECT a.memoId , a.content , a.tripUser.user.userId FROM Memo a WHERE a.tripUser.user.userId = :userId")
    Memo updateByContent(@Param("userId")Long userId);

}
