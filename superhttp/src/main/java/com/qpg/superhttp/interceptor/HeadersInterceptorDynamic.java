package com.qpg.superhttp.interceptor;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeadersInterceptorDynamic implements Interceptor {
    private final Headers mHeaders;
    private static final Charset UTF8 = Charset.forName("UTF-8");

    public HeadersInterceptorDynamic(Headers headers) {
        this.mHeaders = headers;
    }
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Map<String,String> headers=mHeaders.headers();
        Request.Builder builder = chain.request().newBuilder();
        if (headers != null && headers.size() > 0) {
            Set<String> keys = headers.keySet();
            for (String headerKey : keys) {
                builder.addHeader(headerKey, headers.get(headerKey)).build();
            }
        }
        return chain.proceed(builder.build());
    }
    public interface Headers{
        Map<String,String> headers();
    }
}
