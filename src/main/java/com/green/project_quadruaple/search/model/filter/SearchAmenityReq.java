package com.green.project_quadruaple.search.model.filter;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class SearchAmenityReq {
    @Schema(title = "편의시설 ID 목록", example = "[1,3,7,8]")
    private List<String> amenityId = new ArrayList<>();

    @Schema(title = "검색어", example = "부산")
    private String searchWord;

}

