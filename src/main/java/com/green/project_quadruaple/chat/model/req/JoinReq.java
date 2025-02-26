package com.green.project_quadruaple.chat.model.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinReq {

    private Long roomId;
    private String sender;
    private String error;
}
