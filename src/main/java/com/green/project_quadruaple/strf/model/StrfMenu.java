package com.green.project_quadruaple.strf.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@ToString
public class StrfMenu {

    @Schema(description = "메뉴 가격")
    private int menuPrice;
    @Schema(description = "메뉴 ID")
    private long menuId;
    @Schema(description = "메뉴 이름")
    private String menuTitle;
    @Schema(description = "메뉴 사진")
    private String menuPic;
    @Schema(description = "상품 ID")
    private long strfId;
    private LocalTime openCheckIn;
    private LocalTime closeCheckIn;
    private int maxCapacity;
    private int recomCapacity;
}

