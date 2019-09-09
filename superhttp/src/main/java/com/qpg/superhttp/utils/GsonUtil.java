package com.qpg.superhttp.utils;

import com.google.gson.Gson;

/**
 * @Description: Gson单例操作
 */
public class GsonUtil{
    private static Gson gson;

    public static Gson gson() {
        if (gson == null) {
            synchronized (Gson.class) {
                if (gson == null) {
                    gson = new Gson();
                }
            }
        }
        return gson;
    }
}
