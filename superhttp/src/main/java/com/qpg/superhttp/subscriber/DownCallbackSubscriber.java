package com.qpg.superhttp.subscriber;

import com.qpg.superhttp.callback.BaseCallback;

/**
 * @Description: 包含下载进度回调的订阅者
 */
public class DownCallbackSubscriber<T> extends ApiCallbackSubscriber<T> {
    public DownCallbackSubscriber(BaseCallback<T> callBack) {
        super(callBack);
    }

    @Override
    public void onComplete() {
        super.onComplete();
        callBack.onSuccess(super.data);
    }
}
