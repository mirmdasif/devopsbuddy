package net.asifhossain.devopsbuddy.utils;

import net.asifhossain.devopsbuddy.enums.PlansEnum;

public class PlansUtil {
    public static boolean isValidPlan(int planId) {
        return planId == PlansEnum.BASIC.getId() || planId == PlansEnum.PRO.getId();
    }
}
