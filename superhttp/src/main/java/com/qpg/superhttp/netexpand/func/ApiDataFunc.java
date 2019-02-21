package com.qpg.superhttp.netexpand.func;

import com.qpg.superhttp.netexpand.common.ResponseHelper;
import com.qpg.superhttp.netexpand.mode.ApiResult;

import io.reactivex.functions.Function;

/**
 * @Description: ApiResult<T>转T
 */
public class ApiDataFunc<T> implements Function<ApiResult<T>, T> {
    public ApiDataFunc() {
    }

    @Override
    public T apply(ApiResult<T> response) throws Exception {
        if (ResponseHelper.isSuccess(response)) {
            return response.getData();
        }
        return null;
    }
}
