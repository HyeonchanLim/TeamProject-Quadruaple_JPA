package com.green.project_quadruaple.strf.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class StrfParlorDto {
    private int maxCapacity;  // 최대 수용 인원
    private int recomCapacity; // 추천 수용 인원
    private int surcharge; // 추가 요금
    private String title;
//    @JsonIgnore
    private Long menuId;  // 추가된 menuId 필드
//    private List<StrfParlor> parlors;

    private long roomId;
    private List<Integer> roomNum;
//    private List<StrfRooms> rooms;
}
