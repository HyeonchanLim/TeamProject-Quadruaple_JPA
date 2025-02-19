package com.green.project_quadruaple.wishlist.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.query.sql.internal.ParameterRecognizerImpl;

@ToString
@Getter
@Setter
@EqualsAndHashCode
public class WishlistFestRes {
    private Long strfId;
    private Long userId;
    private String locationName;
    private String strfPic;
    private String category;
    private String strfTitle;
    private String startAt;
    private String endAt;
    private Double ratingAVG;
    private int wishCnt;
    private int ratingCnt;
    private int reviewed;

    private boolean isMore;

}
