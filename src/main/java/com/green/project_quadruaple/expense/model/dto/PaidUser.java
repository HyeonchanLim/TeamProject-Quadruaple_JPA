package com.green.project_quadruaple.expense.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@EqualsAndHashCode
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PaidUser {
    private long userId;
    private String name;
    private String profilePic;
}
