package com.green.project_quadruaple.datamanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDummyReq {
    @JsonIgnore
    private Long reviewId;
    private String content;
    private int rating;
    private Long strfId;
    List<PicDto> pics;

}
