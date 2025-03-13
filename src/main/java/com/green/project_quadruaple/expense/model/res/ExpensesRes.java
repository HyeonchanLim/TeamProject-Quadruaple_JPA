package com.green.project_quadruaple.expense.model.res;

import com.green.project_quadruaple.expense.model.dto.DutchPaidUserDto;
import com.green.project_quadruaple.expense.model.dto.ExpenseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@Builder
public class ExpensesRes {
    private String title;
    private String tripPeriod; //startAt ~ endAt
    private int myTotalPrice; //tripId와 userId에 해당하는 전체를 합한 금액
    private int tripTotalPrice; //tripId에 해당하는 전체를 합한 금액
    private List<ExpenseDto> expensedList; //deId에 해당하는 결제 리스트
}
