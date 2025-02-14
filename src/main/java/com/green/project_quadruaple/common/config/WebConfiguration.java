package com.green.project_quadruaple.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    private final String uploadPath;
    public WebConfiguration(@Value("${file.directory}") String uploadPath) {
        this.uploadPath = uploadPath;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*");
//         registry.addMapping("/**").allowedOriginPatterns("http://*").allowedMethods("*").allowedHeaders("*").allowCredentials(true);
//         registry.addMapping("/**").allowedOriginPatterns("https://*").allowedMethods("*").allowedHeaders("*").allowCredentials(true);
//         registry.addMapping("/**").allowedOriginPatterns("https://t1.kakaocdn.net/*").allowedMethods("*").allowedHeaders("*").allowCredentials(true);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) { //이 메소드는 spring이 호출해줌
        registry.addResourceHandler("/pic/**").addResourceLocations("file:" + uploadPath+"/");

        //새로고침 시 화면이 나타날 수 있도록 세팅
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")  //classpath가 resources 폴더 말하는거임
                .resourceChain(true)
                .addResolver(new PathResourceResolver(){
                    @Override
                    protected Resource getResource(String resourcePath, Resource location) throws IOException {
                        Resource resource=location.createRelative(resourcePath);
                        if(resource.exists()&&resource.isReadable()){
                            //리눅스의 경우 파일을 읽기만 가능하거나 쓰기만 가능하거나 권한 지정이 가능함
                            return resource;
                        }
                        return new ClassPathResource("/static/index.html"); //static에 있는 파일이 아닌 경로를 요청하면 index 파일 불러옴
                    }
                });
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        // RestController의 모든 URL에 "/api" prefix를 설정
        configurer.addPathPrefix("api", HandlerTypePredicate.forAnnotation(RestController.class, Controller.class));
    }
}
