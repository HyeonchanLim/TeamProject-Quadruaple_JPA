package com.green.project_quadruaple.point.model.res;

import com.green.project_quadruaple.point.model.dto.RefundableDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
public class RefundableRes {
    private List<RefundableDto> refundableList;
    private int remainPoint;
}
