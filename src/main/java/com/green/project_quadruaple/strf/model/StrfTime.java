package com.green.project_quadruaple.strf.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class StrfTime {
    private String busiNum;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime openCheckIn;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime closeCheckOut;
}
