package com.green.project_quadruaple.common.config.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {
    USER("유저", "USER"),
    ADMIN("관리자", "ADMIN"),
    BUSI("사업자", "BUSI");

    private final String name;
    private final String value;

    public static UserRole getKeyByName(String name) {
        for (UserRole userRole : UserRole.values()) {
            if (userRole.getName().equals(name)) {
                return userRole;
            }
        }
        return null;
    }
}
