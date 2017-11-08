package com.qpg.superhttp.subscriber;

import com.qpg.superhttp.callback.BaseCallback;
import com.qpg.superhttp.exception.ApiException;

/**
 * @Description: 包含回调的订阅者，如果订阅这个，上层在不使用订阅者的情况下可获得回调
 */
public class ApiCallbackSubscriber<T> extends ApiSubscriber<T> {

    BaseCallback<T> callBack;
    T data;

    public ApiCallbackSubscriber(BaseCallback<T> callBack) {
        if (callBack == null) {
            throw new NullPointerException("this callback is null!");
        }
        this.callBack = callBack;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (callBack != null) {
            callBack.onStart();
        }
    }

    @Override
    public void onError(ApiException e) {
        if (e == null) {
            callBack.onFail(-1, "This ApiException is Null.");
            return;
        }
        callBack.onFail(e.getCode(), e.getMessage());
    }

    @Override
    public void onNext(T t) {
        this.data = t;
        callBack.onSuccess(t);
    }

    @Override
    public void onComplete() {
        if (callBack != null) {
            callBack.onCompleted();
        }
    }
    public T getData() {
        return data;
    }
}
