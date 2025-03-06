package com.green.project_quadruaple.businessmypage.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
public class BusinessMyPagePointList {
    private int amount;
    private LocalDateTime usedAt;
    private boolean refund; //(0 = false, 1 = true)
    private String title;
    private long totalAmount;
}
