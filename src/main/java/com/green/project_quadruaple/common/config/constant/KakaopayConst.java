package com.green.project_quadruaple.common.config.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "kakao-api-const")
@RequiredArgsConstructor
@ToString
public class KakaopayConst {
    private final String authKey;
    private final String secretKey;
    private final String affiliateCode;
    private final String url;
}