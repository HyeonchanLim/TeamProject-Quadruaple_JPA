package com.green.project_quadruaple.businessmypage.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
public class BusinessMyPagePointDetail {
    private long pointHistoryId;
    private long strfId;
    private long userId;
    private long menuId;
    private String title;
    private int amount;
    private LocalDateTime createdAt;
}
