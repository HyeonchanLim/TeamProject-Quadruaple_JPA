package com.green.project_quadruaple.strf.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StrfRestDateRes {
    private static final Map<Integer, String> restDayMap = new HashMap<>();

    static {
        restDayMap.put(0, "sun");
        restDayMap.put(1, "mon");
        restDayMap.put(2, "tue");
        restDayMap.put(3, "wed");
        restDayMap.put(4, "thu");
        restDayMap.put(5, "fri");
        restDayMap.put(6, "sat");
    }

    private List<String> restDays = new ArrayList<>();

    public void addRestDays(List<Integer> restKeys) {
        for (Integer key : restKeys) {
            String value = restDayMap.get(key);
            if (value != null) {
                restDays.add(value);
            }
        }
    }

    public List<String> getRestDays() {
        return restDays;
    }
}
