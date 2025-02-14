package com.green.project_quadruaple.user.model;

import com.green.project_quadruaple.common.config.jwt.UserRole;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class UserSelOne {
    private long userId;
    private String email;
    private String pw;
    private String name;
    private String profilePic;
    @Setter
    private List<UserRole> roles;
}
