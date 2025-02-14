package com.green.project_quadruaple.common.model;

import com.green.project_quadruaple.common.config.enumdata.ResponseCode;
import lombok.Getter;

@Getter
public class ResponseWrapper<T> {

    private final String code;
    private final T data;

    public ResponseWrapper(String code, T data) {
        this.code = code;
        this.data = data;
    }
}
