package com.green.project_quadruaple.review.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.sql.internal.ParameterRecognizerImpl;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ReviewReplyReq {
    private Long reviewId;
    private String content;

}
