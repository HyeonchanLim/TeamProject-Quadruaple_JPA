package com.green.project_quadruaple.wishlist.model.wishlistDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WishListResponse {
    private String title;
    private String locationTitle;
    private String strfPic;
    private double ratingAvg;
    private int wishCnt;
    private int ratingCnt;
    private long strfId;
    private String category;
    private String startAt;
    private String endAt;
}
