package com.green.project_quadruaple.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class UserUpdateReq {
    @JsonIgnore
    private long signedUserId;
    private String name;
    private String tell;
    private LocalDate birth;
    @JsonIgnore
    private String profilePic;
}
