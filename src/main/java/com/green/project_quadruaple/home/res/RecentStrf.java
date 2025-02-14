package com.green.project_quadruaple.home.res;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class RecentStrf {
    private String category;
    private List<RecentDto> recent;
}
