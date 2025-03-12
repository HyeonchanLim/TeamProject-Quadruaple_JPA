package com.green.project_quadruaple.busi.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class BusiStrfDto {
    private final Long strfId;
    private final String title;
    private final String category;
    private final String busiNum;
}
