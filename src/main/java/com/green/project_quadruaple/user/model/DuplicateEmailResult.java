package com.green.project_quadruaple.user.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class DuplicateEmailResult {
    private int emailCount;
    private int userIdCount;
}

