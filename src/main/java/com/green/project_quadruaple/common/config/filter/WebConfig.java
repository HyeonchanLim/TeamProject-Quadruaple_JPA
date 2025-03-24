package com.green.project_quadruaple.common.config.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {
    @Bean
    public FilterRegistrationBean<RateTimeFilter> rateLimitFilter() {
        FilterRegistrationBean<RateTimeFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new RateTimeFilter());
        registrationBean.addUrlPatterns("/api/*"); // 특정 경로만 제한
        return registrationBean;
    }
}
/*
registrationBean.addUrlPatterns("/api/*");
/api/ 아래의 경로만 제한하도록 설정하고 정적 파일이나 다른 페이지에는 영향이 없도로 설정함
 */