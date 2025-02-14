package com.green.project_quadruaple.expense.model.res;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.green.project_quadruaple.expense.model.dto.PaidUser;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString(callSuper = true)
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TripUser extends PaidUser {
    private Boolean isJoin;
}
