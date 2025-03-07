package com.green.project_quadruaple.busi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.green.project_quadruaple.common.config.security.SignInProviderType;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Builder
@ToString
public class BusiUserInfoDto {
    @JsonIgnore
    private final long signedUserId;
    private final String email;
    private final String name;
    private final String tell;
    private final LocalDate birth;
    private final String profilePic;
    private final SignInProviderType providerType;
    private final String busiNum;
    private final String category;
}
