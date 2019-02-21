package com.qpg.superhttp.request;


import com.qpg.superhttp.SuperHttp;
import com.qpg.superhttp.callback.BaseCallback;
import com.qpg.superhttp.core.ApiManager;
import com.qpg.superhttp.mode.CacheResult;
import com.qpg.superhttp.mode.MediaTypes;
import com.qpg.superhttp.subscriber.ApiCallbackSubscriber;

import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @Description: Patch请求
 */
public class PatchRequest extends BaseHttpRequest<PatchRequest> {

    protected RequestBody requestBody;
    protected MediaType mediaType;
    protected String content;

    public PatchRequest(String suffixUrl) {
        super(suffixUrl);
    }

    @Override
    protected <T> Observable<T> execute(Type type) {

        if (requestBody != null) {
            return apiService.patchBody(suffixUrl, requestBody).compose(this.<T>norTransformer(type));
        }
        if (content != null && mediaType != null) {
            requestBody = RequestBody.create(mediaType, content);
            return apiService.patchBody(suffixUrl, requestBody).compose(this.<T>norTransformer(type));
        }

        return apiService.patch(suffixUrl, params).compose(this.<T>norTransformer(type));
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
        if (isLocalCache) {
            this.cacheExecute(getSubType(callback)).subscribe(disposableObserver);
        } else {
            this.execute(getType(callback)).subscribe(disposableObserver);
        }
    }

    public PatchRequest setJson(String json) {
        this.content = json;
        this.mediaType = MediaTypes.APPLICATION_JSON_TYPE;
        return this;
    }
}
