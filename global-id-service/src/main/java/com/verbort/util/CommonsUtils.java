package com.verbort.util;

import java.util.Arrays;

public class CommonsUtils {

    private static final String[] switch_on = {"on","true","ON","TRUE"};
    private static final String[] switch_off = {"off","false","OFF","FALSE"};

    public static boolean isSitchOn(String property) {
        if (Arrays.asList(switch_on).contains(property)){
            return true;
        }
        return false;
    }
}
