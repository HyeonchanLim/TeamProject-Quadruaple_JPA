package com.green.project_quadruaple.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserUpdateReq {
    @JsonIgnore
    private long signedUserId;
    private String email;
    private String name;
    @JsonIgnore
    private String profilePic;
}
