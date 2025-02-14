package com.green.project_quadruaple.memo.model.Req;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
@EqualsAndHashCode
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MemoUpReq {
    private Long memoId;
    private Long tripId;
    private String content;
    @JsonIgnore
    private Long tripUserId;
    @JsonIgnore
    private Long userId;
}
