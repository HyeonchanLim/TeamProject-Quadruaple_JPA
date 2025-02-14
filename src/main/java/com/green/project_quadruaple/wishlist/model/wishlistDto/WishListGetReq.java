package com.green.project_quadruaple.wishlist.model.wishlistDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WishListGetReq {
    private List<String> categoryList;
    private int page;


}
