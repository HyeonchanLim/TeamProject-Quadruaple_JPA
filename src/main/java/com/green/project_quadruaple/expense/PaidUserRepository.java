package com.green.project_quadruaple.expense;

import com.green.project_quadruaple.entity.model.DailyExpense;
import com.green.project_quadruaple.entity.model.PaidUser;
import com.green.project_quadruaple.entity.model.PaidUserIds;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaidUserRepository extends JpaRepository<PaidUser, PaidUserIds> {
    Optional<List<PaidUser>> findAllByDailyExpense(DailyExpense dailyExpense);
}
