package com.green.project_quadruaple.common.config.enumdata;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseCode {
    // 성공
    OK("200 성공"),

    // 인증 실패
    UNAUTHORIZED("401 인증만료"),
    // 권한 없음
    Forbidden("403 권한없음"),
    // 결제요청
    PAYMENT_REQUIRED("402 결제요청"),
    //매핑 정보 오류
    NOT_FOUND("404 URL 정보 없음"),
    //잘못된 요청
    BAD_REQUEST("400 잘못된 요청"),
    //유저 정보 없음
    NOT_FOUND_USER("400 유저 정보 없음"),
    // 요청 좌표 정보가 잘못됨
    WRONG_XY_VALUE("400 거리가 너무 가깝거나 잘못된 값입니다."),
    //규격 에러
    NOT_Acceptable("406 규격에러"),
    //서버 에러
    SERVER_ERROR("500 서버에러"),
    //잘못된 응답
    BAD_GATEWAY("502 잘못된 응답"),
    //응답시간만료
    GATEWAY_TIMEOUT("503 응답시간만료"),
    //요청 횟수 초과
    TOO_MANY_REQUESTS("429 요청 횟수 초과"),
    //잘못된 비밀번호
    UNAUTHORIZED_PASSWORD("401 잘못된 비밀번호"),
    //수령한 쿠폰
    ALREADY_RECEIVED_COUPON("601 수령한 쿠폰입니다"),
    //잘못된 쿠폰번호
    NOT_FOUND_COUPON("600 잘못된 쿠폰 번호");

    private final String code;

}
