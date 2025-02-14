package com.green.project_quadruaple.home.res;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RecommendFest {
    private long strfId;
    private String strfPic;
    private String festTitle;
    private boolean isOpen;
    private String startAt;
    private String endAt;
    private String locationTitle;
}
