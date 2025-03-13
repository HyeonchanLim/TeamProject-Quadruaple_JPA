package com.green.project_quadruaple.strf.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class StrfRest {
    private long strfId;
    private String busiNum;
    private List<String> restDates;
}
