package com.green.project_quadruaple.common.config.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;
    private final static String HEADER_AUTHORIZATION = "Authorization";
    private final static String TOKEN_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();
        log.info("uri: {}", uri);
        log.info("ip Address: {}", request.getRemoteAddr());
        String authorizationHeader = request.getHeader(HEADER_AUTHORIZATION);  //"Bearer 토큰값"
        log.info("authorizationHeader: {}", authorizationHeader);
        String token = getAccessToken(authorizationHeader);
//        if(uri.equals("/api/notice")){ //해당 uri로 요청하면
//            //파라미터로 token 값을 받도록 설정
//            token = request.getParameter("token");
//        }else {
//            token = getAccessToken(authorizationHeader);
//        }

        getAuthenticationFromToken(token, request);

        log.info("token: {}", token);


        filterChain.doFilter(request, response);
    }

    private void getAuthenticationFromToken(String token, HttpServletRequest request) {
        if (token != null) {
            try {
                // 토큰에서 Authentication 객체를 얻어옴
                Authentication auth = tokenProvider.getAuthentication(token);
                if (auth != null) {
                    log.info("Authentication is set: {}", auth);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                } else {
                    log.warn("Authentication is null.");
                }
            } catch (ExpiredJwtException e) {
                request.setAttribute("exception", "ExpiredJwtException");
                log.error("Error during authentication", e);
            }
        }
    }


    private String getAccessToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) { // 문자열이 TOKEN_PREFIX 로 시작하는지 확인
            return authorizationHeader.substring(TOKEN_PREFIX.length());
        }
        return null;
    }
}