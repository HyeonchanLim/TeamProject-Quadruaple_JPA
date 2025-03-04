package com.green.project_quadruaple.strf.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StrfParlor {
    private long menuId;
    private int maxCapacity;
    private int recomCapacity;
    private int surcharge;
}
