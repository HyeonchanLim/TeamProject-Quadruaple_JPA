package com.green.project_quadruaple.point.model.res;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QRPointRes {
    private Long strfId;
    private String strfTitle;
    private double lat;
    private double lng;
    private int amount;
}
