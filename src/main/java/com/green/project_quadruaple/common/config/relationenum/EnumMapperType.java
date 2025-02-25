package com.green.project_quadruaple.common.config.relationenum;

import com.fasterxml.jackson.annotation.JsonValue;

public interface EnumMapperType {

    String getCode();
    @JsonValue
    String getValue();
}

