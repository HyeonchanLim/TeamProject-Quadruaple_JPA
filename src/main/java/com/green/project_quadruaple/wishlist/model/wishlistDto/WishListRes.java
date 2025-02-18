package com.green.project_quadruaple.wishlist.model.wishlistDto;

import com.green.project_quadruaple.trip.model.Category;
import lombok.*;

@ToString
@Getter
@Setter
@EqualsAndHashCode
public class WishListRes {
    private long strfId;// 찜 항목 ID
    private String strfTitle;
    private String locationName;
    private double ratingAvg;
    private int wishCnt;
    private double ratingCnt;
    private String strfPic;
    private String category;
    private boolean reviewed;
    private String startAt;
    private String endAt;


    private boolean isMore;




}
