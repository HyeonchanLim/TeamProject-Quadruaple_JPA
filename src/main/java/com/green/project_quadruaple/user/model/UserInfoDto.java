package com.green.project_quadruaple.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@ToString
public class UserInfoDto {
    @JsonIgnore
    private final long signedUserId;
    private final String email;
    private final String name;
    private final String tell;
    private final LocalDate birth;
    private final String profilePic;
}
