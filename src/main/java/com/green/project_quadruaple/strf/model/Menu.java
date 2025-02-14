package com.green.project_quadruaple.strf.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Menu {

    @Schema(description = "메뉴 가격")
    private int menuPrice;
    @Schema(description = "메뉴 ID")
    private String menuId;
    @Schema(description = "메뉴 이름")
    private String menuTitle;
    @Schema(description = "메뉴 사진")
    private String menuPic;

}

