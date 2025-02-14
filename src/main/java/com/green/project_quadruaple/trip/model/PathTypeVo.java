package com.green.project_quadruaple.trip.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public class PathTypeVo {
    private final int pathType;
    private final PathInfoVo info;
}
