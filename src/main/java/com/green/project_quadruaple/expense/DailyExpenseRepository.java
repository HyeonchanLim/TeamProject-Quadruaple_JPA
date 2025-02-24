package com.green.project_quadruaple.expense;

import com.green.project_quadruaple.expense.entity.DailyExpense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyExpenseRepository extends JpaRepository<Long, DailyExpense> {
}
