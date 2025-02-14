package com.green.project_quadruaple.search.model.filter;

import com.green.project_quadruaple.trip.model.Category;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class SearchCategoryReq {
    private Category category;
    private String searchWord;

}
