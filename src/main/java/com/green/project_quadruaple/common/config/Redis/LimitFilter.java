package com.green.project_quadruaple.common.config.Redis;

//import com.lsy.requestlimittest1.service.LimitService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class LimitFilter extends GenericFilterBean {
    private final LimitService service;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String clientIp = httpRequest.getRemoteAddr();
        //isAllow()의 값이 false인 경우엔 429 Too Manny Request를 반환
        if(!service.isAllowed(clientIp)) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setStatus(429);
            httpResponse.getWriter().write("Too Many Requests!!!!!");
            return;
        }
        //isAllow()가 true인 경우 다음 흐름으로 넘김
        chain.doFilter(request, response);
    }
}
