package com.qpg.superhttp.netexpand.convert;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * @Description: ResponseBody to T
 */
final class JsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    JsonResponseBodyConverter() {
    }

    @Override
    public T convert(@NonNull ResponseBody value) throws IOException {
        return (T) value.string();
    }
}
