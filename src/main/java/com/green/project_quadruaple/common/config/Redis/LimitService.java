//package com.green.project_quadruaple.common.config.Redis;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.stereotype.Service;
//
//import java.time.Duration;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class LimitService {
//    private final StringRedisTemplate redisTemplate;
//    private static final int LIMIT = 30;           // 최대 요청 횟수
//    private static final int WINDOW_SECONDS = 120; // 제한 시간 (초)
//
//    public boolean isAllowed(String clientIp) {
//        String key = "rate_limit:" + clientIp;
//        Long currentCount = redisTemplate.opsForValue().increment(key);
//
//        log.info("isAllowed Request~~~~");
//
//        if (currentCount == 1) {
//            Long hasTTL = redisTemplate.getExpire(key);
//            if (hasTTL == null || hasTTL < 0) {
//                redisTemplate.expire(key, Duration.ofSeconds(WINDOW_SECONDS));
//            }
//        }
//        return currentCount <= LIMIT;
//    }
//}
