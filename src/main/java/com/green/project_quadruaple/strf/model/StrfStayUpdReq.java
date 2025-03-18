package com.green.project_quadruaple.strf.model;

import com.green.project_quadruaple.entity.model.*;
import com.green.project_quadruaple.trip.model.Category;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class StrfStayUpdReq {
    private long strfId;
    private String busiNum;
    private String category;
    private long menuId;

//    private List<Long> ameniPoints;
    private List<StrfParlor> parlors;
    private int rooms;
}
