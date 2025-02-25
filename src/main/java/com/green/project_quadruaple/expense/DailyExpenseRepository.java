package com.green.project_quadruaple.expense;

import com.green.project_quadruaple.entity.model.DailyExpense;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface DailyExpenseRepository extends JpaRepository<DailyExpense, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE DailyExpense d SET d.expenseFor = :paidFor WHERE d.deId = :deId")
    int updatePaidFor(@Param("deId") Long deId, @Param("paidFor") String paidFor);


}
