package com.green.project_quadruaple.tripreview.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TripReviewPostReq {
    @JsonIgnore
    private long tripReviewId;
    @Schema(title="여행 ID", example = "1")
    private long tripId;
    @JsonIgnore
    private long userId;
    @Schema(title="여행기 제목", example = "여행기 제목")
    private String title;
    @Schema(title="여행기 내용", example = "이번 여행은 어떠셨나요? 여행에 대한 감상과 여행에서 경험한 꿀팁들을 남겨 다른 회원님들과 공유해보세요!")
    private String content;
}
