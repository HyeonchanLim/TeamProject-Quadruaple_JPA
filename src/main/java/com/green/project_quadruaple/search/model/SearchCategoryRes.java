package com.green.project_quadruaple.search.model;

import com.green.project_quadruaple.review.model.SearchCategoryDto;
import com.green.project_quadruaple.trip.model.Category;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class SearchCategoryRes {
    private List<SearchCategoryDto> dtoList;

    private boolean isMore;

}