package com.qpg.superhttp.netexpand.request;

import com.qpg.superhttp.func.ApiRetryFunc;
import com.qpg.superhttp.netexpand.func.ApiDataFunc;
import com.qpg.superhttp.netexpand.mode.ApiResult;
import com.qpg.superhttp.request.BaseHttpRequest;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @Description: 返回APIResult的基础请求类
 * @date: 17/5/28 15:46.
 */
public abstract class ApiBaseRequest<R extends ApiBaseRequest> extends BaseHttpRequest<R> {
    public ApiBaseRequest(String suffixUrl) {
        super(suffixUrl);
    }

    protected <T> ObservableTransformer<ApiResult<T>, T> apiTransformer() {
        return new ObservableTransformer<ApiResult<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<ApiResult<T>> apiResultObservable) {
                return apiResultObservable
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(new ApiDataFunc<T>())
                        .retryWhen(new ApiRetryFunc(retryCount, retryDelayMillis));
            }
        };
    }
}
