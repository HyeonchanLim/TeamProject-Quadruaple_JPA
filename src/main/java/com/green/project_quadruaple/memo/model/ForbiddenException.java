package com.green.project_quadruaple.memo.model;

import com.green.project_quadruaple.common.config.enumdata.ResponseCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ForbiddenException extends ResponseStatusException {
    public ForbiddenException(String code, String message) {
        super(HttpStatus.FORBIDDEN, code + ": " + message); // code와 message를 합쳐 전달
    }

    public ForbiddenException(String message) {
        this(HttpStatus.FORBIDDEN.value() + "", message); // code가 없는 경우 기본 처리
    }

    }
