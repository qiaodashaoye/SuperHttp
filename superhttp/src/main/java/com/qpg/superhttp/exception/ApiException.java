package com.qpg.superhttp.exception;

import android.net.ParseException;

import com.google.gson.JsonParseException;
import com.qpg.superhttp.mode.ApiCode;

import org.json.JSONException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.HttpException;

/**
 * @Description: API异常统一管理
 */
public class ApiException extends Exception {

    private int code;
    private String message;

    public ApiException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
        this.message = throwable.getMessage();
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public ApiException setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getDisplayMessage() {
        return message + "(code:" + code + ")";
    }

    public static ApiException handleException(Throwable e) throws IOException {
        ApiException ex;
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            ex = new ApiException(e, ApiCode.Request.HTTP_ERROR);
            ex.code=httpException.code();
            ex.message=httpException.response().errorBody().string();
//            switch (httpException.code()) {
//                case ApiCode.Http.Bad_Request:
//                    ex.message=httpException.response().errorBody().string();
//                    break;
//                case ApiCode.Http.UNAUTHORIZED:
//                    ex.message=httpException.response().errorBody().string();
//                    break;
//                case ApiCode.Http.FORBIDDEN:
//                    ex.message=httpException.response().errorBody().string();
//                    break;
//                case ApiCode.Http.NOT_FOUND:
//                    ex.message=httpException.response().errorBody().string();
//                    break;
//                case ApiCode.Http.REQUEST_TIMEOUT:
//                    ex.message=httpException.response().errorBody().string();
//                    break;
//                case ApiCode.Http.GATEWAY_TIMEOUT:
//                    ex.message=httpException.response().errorBody().string();
//                    break;
//                case ApiCode.Http.INTERNAL_SERVER_ERROR:
//                    ex.message=httpException.response().errorBody().string();
//                    break;
//                case ApiCode.Http.BAD_GATEWAY:
//                    ex.message=httpException.response().errorBody().string();
//                    break;
//                case ApiCode.Http.SERVICE_UNAVAILABLE:
//                    ex.message=httpException.response().errorBody().string();
//                    break;
//                default:
//                    ex.message = "NETWORK_ERROR";
//                    break;
//            }
            return ex;
        } else if (e instanceof JsonParseException || e instanceof JSONException || e instanceof ParseException) {
            ex = new ApiException(e, ApiCode.Request.PARSE_ERROR);
            ex.message = "PARSE_ERROR";
            return ex;
        } else if (e instanceof ConnectException) {
            ex = new ApiException(e, ApiCode.Request.NETWORK_ERROR);
            ex.message = "NETWORK_ERROR";
            return ex;
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            ex = new ApiException(e, ApiCode.Request.SSL_ERROR);
            ex.message = "SSL_ERROR";
            return ex;
        } else if (e instanceof SocketTimeoutException) {
            ex = new ApiException(e, ApiCode.Request.TIMEOUT_ERROR);
            ex.message = "TIMEOUT_ERROR";
            return ex;
        } else {
            ex = new ApiException(e, ApiCode.Request.UNKNOWN);
            ex.message = "UNKNOWN";
            return ex;
        }
    }

}
