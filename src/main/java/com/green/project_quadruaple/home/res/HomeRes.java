package com.green.project_quadruaple.home.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
public class HomeRes {
    private final List<RecommendFest> festivalList;
    private final List<PopularLocation> locationList;
    private final List<RecentStrf> recentList;
    private final List<RecommendStrf> recommendList;
}
