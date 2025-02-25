package com.green.project_quadruaple.common.config.relationenum;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnumCodeConfiguration {
    @Bean
    public EnumMapper enumMapper(){
        EnumMapper enumMapper = new EnumMapper();


        return enumMapper;
    }
}
