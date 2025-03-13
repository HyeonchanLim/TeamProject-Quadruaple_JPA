package com.green.project_quadruaple.common.config.Redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redis")
@RequiredArgsConstructor
@Slf4j
public class LimitController {
    private final LimitService service;

    @GetMapping("hello")
    public String hello(){
        return "Hello~ ";
    }
}
