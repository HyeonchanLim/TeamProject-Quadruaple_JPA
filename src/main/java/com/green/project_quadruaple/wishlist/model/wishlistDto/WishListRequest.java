package com.green.project_quadruaple.wishlist.model.wishlistDto;

import java.util.List;

public class WishListRequest {
    private List<String> categoryList; // 카테고리 리스트
    private int page; // 페이지 번호

    // 기본값 설정
    public WishListRequest() {
        this.page = 1; // 기본 페이지는 1
    }

    // Getter 및 Setter
    public List<String> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<String> categoryList) {
        this.categoryList = categoryList;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}

