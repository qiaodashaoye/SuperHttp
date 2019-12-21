package com.qpg.superhttp.netexpand.request;

import com.qpg.superhttp.SuperHttp;
import com.qpg.superhttp.callback.BaseCallback;
import com.qpg.superhttp.core.ApiManager;
import com.qpg.superhttp.lifecycle.BaseLifeCycleObserver;
import com.qpg.superhttp.mode.CacheResult;
import com.qpg.superhttp.netexpand.func.ApiResultFunc;
import com.qpg.superhttp.subscriber.ApiCallbackSubscriber;

import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;

/**
 * @Description: 返回APIResult的GET请求类
 * @date: 17/5/13 14:31.
 */
public class ApiGetRequest extends ApiBaseRequest<ApiGetRequest> {
    public ApiGetRequest(String suffixUrl) {
        super(suffixUrl);
    }

    @Override
    protected <T> Observable<T> execute(Type type) {
        return apiService.get(suffixUrl, params).map(new ApiResultFunc<T>(type)).compose(this.<T>apiTransformer());
    }

    @Override
    protected <T> Observable<CacheResult<T>> cacheExecute(Type type) {
        return this.<T>execute(type).compose(SuperHttp.getApiCache().<T>transformer(cacheMode, type));
    }

    @Override
    protected <T> void execute(BaseCallback<T> callback) {
        DisposableObserver disposableObserver = new ApiCallbackSubscriber(callback);
        if (super.tag != null) {
            ApiManager.get().add(super.tag, disposableObserver);
        }
        if (super.mActivity != null) {
            String tag=String.valueOf(mActivity.getClass().getName()+"_"+System.nanoTime());
            if(!ApiManager.get().isContainTag(tag)){
                mActivity.getLifecycle().addObserver(new BaseLifeCycleObserver(mActivity.getLifecycle(),mActivity));
            }
            ApiManager.get().add(tag, disposableObserver);
        }
        if (super.mFragment != null) {
            String tag=String.valueOf(mFragment.getClass().getName()+"_"+System.nanoTime());
            if(!ApiManager.get().isContainTag(tag)){
                mFragment.getLifecycle().addObserver(new BaseLifeCycleObserver(mFragment.getLifecycle(),mFragment));
            }
            ApiManager.get().add(tag, disposableObserver);
        }
        if (isLocalCache) {
            this.cacheExecute(getSubType(callback)).subscribe(disposableObserver);
        } else {
            this.execute(getType(callback)).subscribe(disposableObserver);
        }
    }
}
