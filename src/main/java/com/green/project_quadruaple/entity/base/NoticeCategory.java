package com.green.project_quadruaple.entity.base;

import lombok.*;

@Getter
@RequiredArgsConstructor
public enum NoticeCategory {
    TRIP("여행", "TRIP"),
    COUPON("쿠폰", "COUPON"),
    CHAT("채팅", "CHAT"),
    AD("광고", "AD"),
    SERVICE("서비스", "SERVICE");

    private final String name;
    private final String value;

    public static NoticeCategory getKeyByName(String name) {
        for (NoticeCategory category : NoticeCategory.values()) {
            if (category.getName().equals(name)) {
                return category;
            }
        }
        return null;
    }
}
