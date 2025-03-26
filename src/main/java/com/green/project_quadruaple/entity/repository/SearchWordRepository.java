package com.green.project_quadruaple.entity.repository;

import com.green.project_quadruaple.entity.model.SearchWord;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SearchWordRepository extends JpaRepository<SearchWord , Long> {
    @Query("SELECT a FROM SearchWord a where a.user.userId = :userId ORDER BY a.searchAt DESC limit 10")
    List<SearchWord> searchByUser(@Param("userId") Long userId);

}
