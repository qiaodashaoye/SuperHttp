package com.qpg.superhttp.subscriber;

import com.qpg.superhttp.exception.ApiException;
import com.qpg.superhttp.mode.ApiCode;

import io.reactivex.observers.DisposableObserver;

/**
 * @Description: API统一订阅者
 */
abstract class ApiSubscriber<T> extends DisposableObserver<T> {

    ApiSubscriber() {

    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof ApiException) {
            onError((ApiException) e);
        } else {
            onError(new ApiException(e, ApiCode.Request.UNKNOWN));
        }
    }

    protected abstract void onError(ApiException e);
}
