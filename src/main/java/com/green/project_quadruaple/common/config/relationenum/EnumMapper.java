package com.green.project_quadruaple.common.config.relationenum;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class EnumMapper {
    private Map<String , List<EnumMapperValue>> factory = new LinkedHashMap<>();

    public void put(String key , Class<? extends EnumMapperType> e){
        factory.put(key,toEnumValues(e));
    }
    private List<EnumMapperValue> toEnumValues(Class<? extends  EnumMapperType> e ){
        return Arrays.stream(e.getEnumConstants())
                .map(EnumMapperValue::new)
                .toList();
    }
    public List<EnumMapperValue> get (String key){
        return factory.get(key);
    }
}
