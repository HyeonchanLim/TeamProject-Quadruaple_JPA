package com.green.project_quadruaple.expense.model.req;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.green.project_quadruaple.expense.model.dto.DeDto;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString(callSuper = true)
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ExpenseInsReq extends DeDto {
    private long tripId;
    private int totalPrice;
    private List<Long> paidUserList;
}
