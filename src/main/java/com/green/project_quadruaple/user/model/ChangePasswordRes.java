package com.green.project_quadruaple.user.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChangePasswordRes {
    private long singedUserId;
    private String pw;
}
