package com.green.project_quadruaple.tripreview.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CopyScheduleDto {
    private long scheduleMemoId;
    private long originalScheduleId;
}
