package com.green.project_quadruaple.expense.model.res;

import com.green.project_quadruaple.expense.model.dto.DutchPaidUserDto;
import lombok.*;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class ExpenseOneRes {
    private long deId;
    private String paidFor;
    private int totalPrice;
    private List<DutchPaidUserDto> payList;
}
