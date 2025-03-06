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
    private Long sumMonth1;
    private Long sumMonth2;
    private Long sumMonth3;
    private Long sumMonth4;
    private Long sumMonth5;
    private Long sumMonth6;
    private Long sumMonth7;
    private Long sumMonth8;
    private Long sumMonth9;
    private Long sumMonth10;
    private Long sumMonth11;
    private Long sumMonth12;
}

