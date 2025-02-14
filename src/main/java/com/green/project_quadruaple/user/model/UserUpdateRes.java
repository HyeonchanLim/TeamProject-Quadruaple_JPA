package com.green.project_quadruaple.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.green.project_quadruaple.common.model.ResultResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class UserUpdateRes {
    private final long signedUserId;
    @JsonIgnore
    private final String pw;
    private final String profilePic;
}
