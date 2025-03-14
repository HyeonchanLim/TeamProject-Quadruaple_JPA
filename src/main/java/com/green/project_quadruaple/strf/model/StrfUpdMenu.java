package com.green.project_quadruaple.strf.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class StrfUpdMenu {
    private long strfId;
    private long menuId;
    private String busiNum;
//    private String category;

    private List<MenuIns> menus;
}
