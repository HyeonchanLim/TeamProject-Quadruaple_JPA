package com.green.project_quadruaple.search.model.filter;

import com.green.project_quadruaple.common.config.enumdata.StrfCategory;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Stay {
    private Long strfId;
    private String title;
    private StrfCategory category;// enum 연결해야해서 enum 클래스 타입으로 선언함
    private String locationName;
    private String strfPic;
    private Double averageRating;
    private int reviewCount;
    private Long reviewWriterUserId;
    private int wishlistCount;
    private int hasMyReview;
    private int wishIn;
    private List<SearchAmenity> amenities;
    private boolean isMore;
}

