package com.green.project_quadruaple.point.model.res;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class QRPointRes {
    private Integer remainPoints;
    private Integer afterRemainPoints;
    private LocalDateTime timeNow;
    private Long strfId;
    private String strfTitle;
    private double lat;
    private double lng;
    private int amount;
}
