package com.green.project_quadruaple.entity.base;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReportEnum {
    REVIEW("리뷰","REVIEW"),
    STRF("상품","STRF"),
    TRIP("여행기","TRIP");

    private final String name;
    private final String value;

    public static ReportEnum getKeyByName(String name) {
        for (ReportEnum reportEnum : ReportEnum.values()) {
            if (reportEnum.getName().equals(name)) {
                return reportEnum;
            }
        }
        return null;
    }

}

