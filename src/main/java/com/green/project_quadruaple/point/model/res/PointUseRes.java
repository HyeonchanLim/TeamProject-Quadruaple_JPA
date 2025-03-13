package com.green.project_quadruaple.point.model.res;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PointUseRes {
    private int remainPoints;
    private int usedPoints;
    private String usedAt;
}
