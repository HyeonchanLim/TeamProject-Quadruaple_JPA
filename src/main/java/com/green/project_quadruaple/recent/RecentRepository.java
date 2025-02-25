package com.green.project_quadruaple.recent;

import com.green.project_quadruaple.entity.model.Recent;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

//public interface RecentRepository extends JpaRepository<Recent , Long> {
//    @Query("SELECT new com.green.project_quadruaple.entity.model.Recent(a.userId, a.strfId , a.undoRecent) FROM Recent a")
//    int recent
//}
//@Query("SELECT new com.green.springjpa.student.model.StudentRes(s.studentId, s.name , s.createdAt) FROM Student s")
//List<StudentRes> findList(Pageable pageable);
