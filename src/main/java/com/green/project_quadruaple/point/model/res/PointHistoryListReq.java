package com.green.project_quadruaple.point.model.res;

import com.green.project_quadruaple.point.model.dto.PointHistoryListDto;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
public class PointHistoryListReq {
    private String userName;
    private int remainPoint;
    private List<PointHistoryListDto> pointList;
}