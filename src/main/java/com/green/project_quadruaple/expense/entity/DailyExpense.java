package com.green.project_quadruaple.expense.entity;

import com.green.project_quadruaple.entity.base.CreatedAt;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DailyExpense extends CreatedAt {
    @Id
    @Column(name = "de_id")
    private Long deId;

    @Column(name = "for", nullable = false, length = 100)
    private String expenseFor;


}
