package com.green.project_quadruaple.strf.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class StrfFestTime {
    private long strfId;
    private String busiNum;
    private LocalDate startAt;
    private LocalDate endAt;

}
