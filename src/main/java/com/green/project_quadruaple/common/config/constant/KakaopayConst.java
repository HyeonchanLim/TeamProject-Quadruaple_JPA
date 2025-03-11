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
    private final String affiliateCode; //cid
    private final String url;
    private final String bookingApprovalUrl;
    private final String bookingCancelUrl;
    private final String bookingFailUrl;
    private final String bookingCompleteUrl;
    private final String pointApprovalUrl;
    private final String pointCancelUrl;
    private final String pointFailUrl;
    private final String pointCompleteUrl;

}