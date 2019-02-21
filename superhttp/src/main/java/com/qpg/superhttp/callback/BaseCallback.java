package com.qpg.superhttp.callback;

/**
 * @Description: 请求接口回调
 */
public abstract class BaseCallback<T> {
    public abstract void onStart();

    public abstract void onSuccess(T data);

    public abstract void onFail(int errCode, String errMsg);

    public abstract void onCompleted();
}
