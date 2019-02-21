package com.qpg.superhttp.netexpand.temp;

/**
 * @Description:
 * @date: 2018/1/20 16:37
 */
public class Utils {
    public static <T> T checkNotNull(T t, String message) {
        if (t == null) {
            throw new NullPointerException(message);
        }
        return t;
    }

    public static <T> T checkIllegalArgument(T t, String message) {
        if (t == null) {
            throw new IllegalArgumentException(message);
        }
        return t;
    }

    public static <T> void checkIllegalArgument(boolean condition, String message) {
        if (condition) {
            throw new IllegalArgumentException(message);
        }
    }
}
