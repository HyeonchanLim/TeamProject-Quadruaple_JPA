package com.green.project_quadruaple.expense.model.dto;

import lombok.*;

import java.util.List;

@ToString
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class ExpenseDto {
    private long deId;
    private String paidFor; //deId마다 있는 expense_for
    private int totalPrice; //deId에 해당하는 전체 합한 금액
    private int myPrice; //deId와 userId에 해당하는 금액
    private List<PaidUserInfo> paidUserInfoList; //deId에 해당하는 인원 리스트
}
