package com.green.project_quadruaple.common.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component // 빈 등록
public class SizeConstants {

    private static int default_page_size;
    private static int default_search_size;

    public SizeConstants(@Value("${const.default-search-size}")int searchSize, @Value("${const.default-review-size}")int reviewSize) {
        default_page_size = searchSize;
        default_search_size = reviewSize;
    }
    public static int getDefault_page_size() {
        return default_page_size;
    }
    public static int getDefault_search_size() {
        return default_search_size;
    }
}
