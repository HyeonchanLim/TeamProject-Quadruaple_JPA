package com.green.project_quadruaple.common.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component // 빈 등록
public class Constants {

    private static int default_page_size;
    private static int default_search_size;


    public void setDefault_page_size(@Value("${const.default-review-size}") int value) {
        default_page_size = value;
    }


    public void setDefault_search_size(@Value("${const.default-search-size}")int value){
        default_search_size = value;
    }

    public static int getDefault_page_size() {
        return default_page_size;
    }
    public static int getDefault_search_size() {
        return default_search_size;
    }
}
