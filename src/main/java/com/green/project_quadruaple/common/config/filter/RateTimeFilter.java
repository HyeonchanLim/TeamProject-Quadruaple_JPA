package com.green.project_quadruaple.common.config.filter;

import io.github.bucket4j.*;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateTimeFilter implements Filter {
    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String path = ((HttpServletRequest) request).getRequestURI();
        if (isStaticResource(path)) {
            chain.doFilter(request, response);
            return;
        }
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String key;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal());
        if (isAuthenticated) {
            key = "USER_" + auth.getName();
        } else {
            key = "IP_" + request.getRemoteAddr();
        }
        Bucket bucket = buckets.computeIfAbsent(key, k -> createBucket(isAuthenticated));
        if (bucket.tryConsume(1)) {
            chain.doFilter(request, response);
        } else {
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            httpServletResponse.setStatus(429);
            httpServletResponse.getWriter().write("429 Too Many Requests");
        }
    }

    private String extractUserId(HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            return auth.getName();
        }
        return null;
    }
    private Bucket createBucket(boolean isAuthenticated) {
        if (isAuthenticated) {
            return Bucket.builder()
                    .addLimit(limit -> limit.capacity(10).refillGreedy(20, Duration.ofSeconds(1))) // 회원: 초당 10회
                    .build();
        } else {
            return Bucket.builder()
                    .addLimit(limit -> limit.capacity(3).refillGreedy(5, Duration.ofSeconds(1))) // 비회원: 초당 3회
                    .build();
        }
    }
    private boolean isStaticResource(String path) {
        return path.startsWith("/assets/") || path.startsWith("/static/") ||
                path.startsWith("/public/") || path.endsWith(".js") ||
                path.endsWith(".css") || path.endsWith(".png") ||
                path.endsWith(".jpg") || path.endsWith(".ico") || path.endsWith(".webp");
    }

//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//        String path = ((HttpServletRequest) request).getRequestURI();
//
//        // 정적 리소스 경로는 필터 제외
//        if (isStaticResource(path)) {
//            chain.doFilter(request, response);
//            return;
//        }
//
//        String ip = request.getRemoteAddr();
//        Bucket bucket = buckets.computeIfAbsent(ip, this::createBucket);
//
//        if (bucket.tryConsume(1)) { // 1 요청 소모
//            chain.doFilter(request, response);
//        } else {
//            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
//            httpServletResponse.setStatus(429); // 429 코드 발생
//            httpServletResponse.getWriter().write("429 Too Many Requests");
//        }
//
//    }
//
//    private Bucket createBucket(String key) {
//        return Bucket.builder()
//                .addLimit(limit -> limit.capacity(10).refillGreedy(10, Duration.ofSeconds(1))) // 초당 10회 제한
//                .build();
//    }
//    // 정적 리소스 경로 체크
//    private boolean isStaticResource(String path) {
//        return path.startsWith("/assets/") || path.startsWith("/static/") ||
//                path.startsWith("/public/") || path.endsWith(".js") ||
//                path.endsWith(".css") || path.endsWith(".png") ||
//                path.endsWith(".jpg") || path.endsWith(".ico") || path.endsWith(".webp");
//    }
}
/*
현재 ip 기반으로 api 요청 횟수 제한을 설정했음
실제 서비스에서는 사용자 id 나 api 키로 제한하는 게 더 좋음

 */
