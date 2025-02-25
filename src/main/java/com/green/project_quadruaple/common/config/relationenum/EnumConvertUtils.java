package com.green.project_quadruaple.common.config.relationenum;

import io.micrometer.common.util.StringUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.EnumSet;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EnumConvertUtils {
    public static <E extends Enum<E> & EnumMapperType> E ofCode(Class<E> enumClass, String code){
        if (StringUtils.isBlank(code)){
            return null;
        }

        return EnumSet.allOf(enumClass)
                .stream()
                .filter(item -> item.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }

    public static <E extends Enum<E> & EnumMapperType> String toCode(E enumItem){
        if (enumItem == null) { return null;}
        return enumItem.getCode();
    }
}
