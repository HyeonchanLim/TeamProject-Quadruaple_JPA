package com.green.project_quadruaple.datamanager.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@EqualsAndHashCode(callSuper = true)
public class MenuReq extends StrfIdGetReq{
    private List<MenuDto> menus;
}
