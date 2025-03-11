package com.green.project_quadruaple.busi.model;

import com.green.project_quadruaple.common.config.jwt.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class BusiUserSignInRes {
    private final long userId;
    private final String name;
    private final String accessToken;
    private final List<UserRole> roles;

    private final List<BusiStrfDto> dtos;
}
