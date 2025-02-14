package com.green.project_quadruaple.search.model;

import com.green.project_quadruaple.search.model.filter.SearchAmenity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class SearchFilter {
    private Long strfId;
    private String strfTitle;
    private String category;
    private String locationName;
    private String strfPic;
    private double averageRating;
    private int reviewCnt;
    private int wishlistCnt;
    private int wishIn;
    private String createdAt;
    private List<SearchAmenity> amenities;

}
