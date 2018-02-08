package com.it2go.framework.util;

public class StringUtils {

    public static boolean exists(String string){
        return string != null && !string.trim().isEmpty();
    }
}
