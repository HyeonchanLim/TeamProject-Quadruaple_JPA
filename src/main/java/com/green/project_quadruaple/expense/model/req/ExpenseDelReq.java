package com.green.project_quadruaple.expense.model.req;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@ToString
@Setter
@Getter
@EqualsAndHashCode
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ExpenseDelReq {
    private long deId;
    private long tripId;
}
