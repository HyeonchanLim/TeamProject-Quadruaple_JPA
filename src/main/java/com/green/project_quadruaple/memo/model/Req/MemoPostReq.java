package com.green.project_quadruaple.memo.model.Req;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.green.project_quadruaple.entity.model.TripUser;
import com.green.project_quadruaple.entity.model.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
@EqualsAndHashCode
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MemoPostReq {
    @JsonIgnore
    private Long scheMemoId;
    private Long tripId;
    private Integer day;
    private Integer seq;
    private String content;

    @JsonIgnore
    private Long tripUserId;
}
