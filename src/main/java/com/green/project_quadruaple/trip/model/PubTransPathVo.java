package com.green.project_quadruaple.trip.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@AllArgsConstructor
@ToString
public class PubTransPathVo {
    private final int searchType;
    private final List<PathTypeVo> path;
}