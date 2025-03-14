package com.green.project_quadruaple.businessmypage.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL) // null 값인 필드는 JSON에서 제외
public class BusinessMyPageSales {
    private String month;
    private double totalSales;
}

