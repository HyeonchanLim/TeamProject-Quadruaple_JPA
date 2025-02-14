package com.green.project_quadruaple.expense.model.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@Setter
@EqualsAndHashCode
public class ExpenseDto {
    private long deId;
    private String paidFor;
    private int totalPrice;
    private int myPrice;
    private List<PaidUser> paidUserList;
}
