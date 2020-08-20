package com.qpg.superhttp.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
/**
 * @Description: Gson单例操作
 */
public class GsonUtil{
    private static Gson gson;

    public static Gson gson() {
        if (gson == null) {
            synchronized (Gson.class) {
                if (gson == null) {
                    gson = (new GsonBuilder()).disableHtmlEscaping().create();
                }
            }
        }
        return gson;
    }
}
