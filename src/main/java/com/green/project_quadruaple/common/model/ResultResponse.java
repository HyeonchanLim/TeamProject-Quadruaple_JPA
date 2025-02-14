package com.green.project_quadruaple.common.model;


import com.green.project_quadruaple.common.config.enumdata.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResultResponse {
    private String code;

    // 성공
    @ResponseStatus(HttpStatus.OK)
    public static ResultResponse success() {
        return new ResultResponse(ResponseCode.OK.getCode());
    }

    // 권한 없음
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public static ResultResponse forbidden() {return new ResultResponse(ResponseCode.Forbidden.getCode());}

    // 잘못된 요청
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static ResultResponse notFound() {return new ResultResponse(ResponseCode.NOT_FOUND.getCode());}

    // 잘못된 요청
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static ResultResponse badRequest() {return new ResultResponse(ResponseCode.BAD_REQUEST.getCode());}

    // 인증실패
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public static ResultResponse unauthorized() {
        return new ResultResponse(ResponseCode.UNAUTHORIZED.getCode());
    }

    // 결제요청
    @ResponseStatus(HttpStatus.PAYMENT_REQUIRED)
    public static ResultResponse paymentRequired() {return new ResultResponse(ResponseCode.PAYMENT_REQUIRED.getCode());}

    // 규격에러
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public static ResultResponse notAcceptable() {
        return new ResultResponse(ResponseCode.NOT_Acceptable.getCode());
    }

    // 서버에러
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public static ResultResponse severError() {
        return new ResultResponse(ResponseCode.SERVER_ERROR.getCode());
    }

    // 잘못된 응답
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public static ResultResponse badGateway() {
        return new ResultResponse(ResponseCode.BAD_GATEWAY.getCode());
    }

    // 잘못된 응답
    @ResponseStatus(HttpStatus.GATEWAY_TIMEOUT)
    public static ResultResponse gatewayTimeout() {
        return new ResultResponse(ResponseCode.GATEWAY_TIMEOUT.getCode());
    }

}
