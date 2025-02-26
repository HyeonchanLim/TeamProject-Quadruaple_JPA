package com.green.project_quadruaple.common.config.security.oauth;

import com.green.project_quadruaple.common.config.jwt.JwtUser;
import com.green.project_quadruaple.common.config.jwt.UserRole;
import lombok.Getter;

import java.util.List;

@Getter
public class OAuth2JwtUser extends JwtUser {
    private final String nickName;
    private final String pic;
    private final String email;

    public OAuth2JwtUser(String nickName, String pic, long signedUserId, List<UserRole> roles, String email) {
        super(signedUserId, roles);
        this.nickName = nickName;
        this.pic = pic;
        this.email = email;
    }
}
