package com.green.project_quadruaple.review.model;

import com.green.project_quadruaple.trip.model.Category;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class SearchCategoryDto {
    private Long strfId;
    private String strfTitle;
    private Category category;
    private String locationName;
    private String strfPic;
    private Double ratingAvg;
    private Integer reviewCount;
    private Integer wishlistCount;
    private Integer wishIn;
    private Integer hasMyReview;
}
