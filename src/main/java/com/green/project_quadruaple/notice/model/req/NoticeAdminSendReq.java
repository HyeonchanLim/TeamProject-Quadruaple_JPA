package com.green.project_quadruaple.notice.model.req;

import com.green.project_quadruaple.common.config.jwt.UserRole;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class NoticeAdminSendReq {
    private String title;
    private String content;
    private UserRole role;

    public NoticeAdminSendReq(String title, String content, String role) {
        this.title = title;
        this.content = content;
        this.role = role!=null? UserRole.getKeyByName(role) : null;
    }
}
