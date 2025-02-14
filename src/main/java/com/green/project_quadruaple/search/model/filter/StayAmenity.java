package com.green.project_quadruaple.search.model.filter;

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
public class StayAmenity {
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
