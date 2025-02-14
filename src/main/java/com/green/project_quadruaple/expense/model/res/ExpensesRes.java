package com.green.project_quadruaple.expense.model.res;

import com.green.project_quadruaple.expense.model.dto.DutchPaidUserDto;
import com.green.project_quadruaple.expense.model.dto.ExpenseDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@Setter
public class ExpensesRes {
    private String title;
    private String tripPeriod;
    private int myTotalPrice;
    private int tripTotalPrice;
    private List<ExpenseDto> expensedList;
}
