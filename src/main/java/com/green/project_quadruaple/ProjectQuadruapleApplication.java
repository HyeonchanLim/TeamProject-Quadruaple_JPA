package com.green.project_quadruaple;

import jakarta.persistence.Persistence;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaAuditing
@SpringBootApplication
@ConfigurationPropertiesScan
@EnableScheduling  // 스케줄러 활성화
public class ProjectQuadruapleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectQuadruapleApplication.class, args);
    }

}
