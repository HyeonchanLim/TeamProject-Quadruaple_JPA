package com.green.project_quadruaple.common.config.security;

import com.green.project_quadruaple.common.config.jwt.JwtUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade {
    public static long getSignedUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof JwtUser) {
            JwtUser jwtUser = (JwtUser) principal;
            return jwtUser.getSignedUserId();
        } else {
            throw new IllegalStateException("Authentication principal is not of type JwtUser");
        }
    }
}