package com.green.project_quadruaple.strf.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class StrfStayInsReq {
    private long strfId;
    private String busiNum;
    private String category;

    private List<MenuIns> menus;
    private List<Long> amenipoints;
    private List<StrfParlor> parlors;
    private List<Long> rooms;
}
