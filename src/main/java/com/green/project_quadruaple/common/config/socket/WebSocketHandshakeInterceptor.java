package com.green.project_quadruaple.common.config.socket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Enumeration;
import java.util.Map;

@Slf4j
@Component
public class WebSocketHandshakeInterceptor implements HandshakeInterceptor {


    @Override
    public boolean beforeHandshake(ServerHttpRequest request,
                                   ServerHttpResponse response,
                                   WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception
    {
        if(request instanceof ServletServerHttpRequest servletRequest) {
            Enumeration<String> authorization = servletRequest.getServletRequest().getHeaders("Authorization");
            Enumeration<String> headerNames = servletRequest.getServletRequest().getHeaderNames();
            while(headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                log.info("name = {}", name);
            }
            return true;
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request,
                               ServerHttpResponse response,
                               WebSocketHandler wsHandler,
                               Exception exception)
    {

    }
}
