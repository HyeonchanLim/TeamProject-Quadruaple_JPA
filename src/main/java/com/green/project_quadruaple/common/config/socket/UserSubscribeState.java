package com.green.project_quadruaple.common.config.socket;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class UserSubscribeState {
    
    // Map<cjId, state>
    public static final Set<Long> USER_SUB_STATE = new HashSet<>();

    public static void changeState(Long cjId) {
        if(USER_SUB_STATE.contains(cjId)) {
            USER_SUB_STATE.remove(cjId);
        } else {
            USER_SUB_STATE.add(cjId);
        }
    }
}
