package com.green.project_quadruaple.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class UserInfo {
    private final long userId;
    private final String email;
    private final String name;
    private final String profilePic;
}
