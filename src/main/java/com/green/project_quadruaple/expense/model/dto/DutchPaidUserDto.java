package com.green.project_quadruaple.expense.model.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@EqualsAndHashCode
public class DutchPaidUserDto {
    private long userId;
    private String profilePic;
    private String name;
    private int price;
}
