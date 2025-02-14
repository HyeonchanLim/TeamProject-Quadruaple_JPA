package com.green.project_quadruaple.common.config.jwt;

import com.green.project_quadruaple.user.exception.UserErrorCode;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final HandlerExceptionResolver resolver;

    // @Qualifier 스프링 컨테이너로 DI 받을 때 여러 빈 중 하나를 선택할 수 있음, ID값을 적으면 됨.
    public JwtAuthenticationEntryPoint(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.resolver = resolver;
    }


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        // 🔴 request에 저장된 예외 확인
        String exception = (String) request.getAttribute("exception");
        if ("ExpiredJwtException".equals(exception)) {
            log.error("ExpiredJwtException detected in EntryPoint");

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(UserErrorCode.EXPIRED_TOKEN.getMessage());
            return;
        }

        // 🔴 기본 404 처리 (사용자가 없을 경우)
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        response.getWriter().write(UserErrorCode.USER_NOT_FOUND.getMessage());
    }
}
