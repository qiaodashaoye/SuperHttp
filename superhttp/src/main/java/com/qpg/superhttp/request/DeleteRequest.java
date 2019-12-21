package com.qpg.superhttp.request;

import com.qpg.superhttp.SuperHttp;
import com.qpg.superhttp.callback.BaseCallback;
import com.qpg.superhttp.core.ApiManager;
import com.qpg.superhttp.lifecycle.BaseLifeCycleObserver;
import com.qpg.superhttp.mode.CacheResult;
import com.qpg.superhttp.subscriber.ApiCallbackSubscriber;

import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;

/**
 * @Description: Delete请求
 */
public class DeleteRequest extends BaseHttpRequest<DeleteRequest> {
    public DeleteRequest(String suffixUrl) {
        super(suffixUrl);
    }

    @Override
    protected <T> Observable<T> execute(Type type) {
        return apiService.delete(suffixUrl, params).compose(this.<T>norTransformer(type));
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
            if(!ApiManager.get().isContainTag(super.mActivity.getClass().getName())){
                super.mActivity.getLifecycle().addObserver(new BaseLifeCycleObserver(super.mActivity.getLifecycle(),super.mActivity));
            }
            ApiManager.get().add(super.mActivity.getClass().getName()+"_"+disposableObserver.hashCode(), disposableObserver);
        }

        if (super.mFragment != null) {
            if(!ApiManager.get().isContainTag(super.mFragment .getClass().getName())){
                super.mFragment .getLifecycle().addObserver(new BaseLifeCycleObserver(super.mFragment .getLifecycle(),mFragment));
            }
            ApiManager.get().add(super.mFragment .getClass().getName()+"_"+disposableObserver.hashCode(), disposableObserver);
        }


        if (isLocalCache) {
            this.cacheExecute(getSubType(callback)).subscribe(disposableObserver);
        } else {
            this.execute(getType(callback)).subscribe(disposableObserver);
        }
    }
}
