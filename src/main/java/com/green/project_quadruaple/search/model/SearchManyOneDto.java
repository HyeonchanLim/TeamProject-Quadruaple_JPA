package com.green.project_quadruaple.search.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class SearchManyOneDto {
    private long menuId;
    private int maxCapacity;
    private int recomCapacity;
    private int surcharge;
    private long roomId;
    private int roomNum;
}
