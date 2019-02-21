package com.qpg.superhttpdemo;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;

import io.reactivex.functions.Function;
import okhttp3.ResponseBody;

/**
 * @Description: 自定义ResponseBody转T，
 * 可根据每个项目返回的数据格式自定义，提高了扩展性
 */
public class CustomApiFunc<T> implements Function<ResponseBody, T> {
    private Type type;

    public CustomApiFunc(Type type) {
        this.type = type;
    }

    @Override
    public T apply(ResponseBody responseBody) throws Exception {
        Gson gson = new Gson();
        String json;
        try {
            json = responseBody.string();
            if (type.equals(String.class)) {
                return (T) json;
            } else {
                return gson.fromJson(json, type);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            responseBody.close();
        }
        return null;
    }
}
