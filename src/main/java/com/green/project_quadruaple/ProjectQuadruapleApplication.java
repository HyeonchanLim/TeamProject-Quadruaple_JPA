package com.green.project_quadruaple;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ProjectQuadruapleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectQuadruapleApplication.class, args);
    }

}
