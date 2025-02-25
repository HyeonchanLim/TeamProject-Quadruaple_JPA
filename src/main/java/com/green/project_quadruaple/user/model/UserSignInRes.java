package com.green.project_quadruaple.user.model;

import com.green.project_quadruaple.common.config.jwt.UserRole;
import com.green.project_quadruaple.common.model.ResultResponse;

import lombok.*;

import java.util.List;

@Getter
@Builder
@ToString
public class UserSignInRes {
    private final long userId;
    private final String name;
    private final String accessToken;
    private final List<UserRole> roles;
}
