package com.green.project_quadruaple.search.model.filter;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class SearchStay {
    private Long strfId;
    private String strfTitle;
    private String category;
    private String locationName;
    private String strfPic;
    private double averageRating;
    private int reviewCnt;
    private int wishIn;
    private String createdAt;

}
