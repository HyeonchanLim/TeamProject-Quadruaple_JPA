package com.green.project_quadruaple.strf.model;

import com.green.project_quadruaple.trip.model.Category;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class StrfInfoInsRes {
    private long strfId;
    private String title;
    private Category category;
}
