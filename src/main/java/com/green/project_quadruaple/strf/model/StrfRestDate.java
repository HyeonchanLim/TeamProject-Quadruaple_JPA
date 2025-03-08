package com.green.project_quadruaple.strf.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StrfRestDate {
    // 휴무일 req key - sun, mon, tue, wed, thu, fri, sat
    // 휴무일 res value - 0, 1, 2, 3, 4, 5, 6
    private static final Map<String, Integer> restDayMap = new HashMap<>();

    static {
        restDayMap.put("sun", 0);
        restDayMap.put("mon", 1);
        restDayMap.put("tue", 2);
        restDayMap.put("wed", 3);
        restDayMap.put("thu", 4);
        restDayMap.put("fri", 5);
        restDayMap.put("sat", 6);
    }

    private List<Integer> restDays = new ArrayList<>();

    public void addRestDays(List<String> restKeys) {
        for (String key : restKeys) {
            Integer value = restDayMap.get(key.toLowerCase());
            if (value != null) {
                restDays.add(value);
            }
        }
    }

    public List<Integer> getRestDays() {
        return restDays;
    }

}
