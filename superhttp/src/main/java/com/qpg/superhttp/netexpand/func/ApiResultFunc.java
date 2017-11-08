package com.qpg.superhttp.netexpand.func;

import android.text.TextUtils;

import com.qpg.superhttp.common.GsonUtil;
import com.qpg.superhttp.netexpand.mode.ApiResult;
import com.qpg.superhttp.netexpand.mode.ResponseCode;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;

import io.reactivex.functions.Function;
import okhttp3.ResponseBody;

/**
 * @Description: ResponseBody转ApiResult<T>
 */
public class ApiResultFunc<T> implements Function<ResponseBody, ApiResult<T>> {
    protected Type type;

    public ApiResultFunc(Type type) {
        this.type = type;
    }

    @Override
    public ApiResult<T> apply(ResponseBody responseBody) throws Exception {
        ApiResult<T> apiResult = new ApiResult<>();
        apiResult.setCode(-1);
        try {
            String json = responseBody.string();
            ApiResult result = parseApiResult(json, apiResult);
            if (result != null) {
                apiResult = result;
                if (apiResult.getData() != null) {
                    T data = GsonUtil.gson().fromJson(apiResult.getData().toString(), type);
                    apiResult.setData(data);
                    apiResult.setCode(ResponseCode.HTTP_SUCCESS);
                } else {
                    apiResult.setMsg("ApiResult's data is null");
                }
            } else {
                apiResult.setMsg("json is null");
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
            apiResult.setMsg(e.getMessage());
        } finally {
            responseBody.close();
        }
        return apiResult;
    }

    private ApiResult parseApiResult(String json, ApiResult apiResult) throws JSONException {
        if (TextUtils.isEmpty(json)) return null;
        JSONObject jsonObject = new JSONObject(json);
        if (jsonObject.has("code")) {
            apiResult.setCode(jsonObject.getInt("code"));
        }
        if (jsonObject.has("data")) {
            apiResult.setData(jsonObject.getString("data"));
        }
        if (jsonObject.has("msg")) {
            apiResult.setMsg(jsonObject.getString("msg"));
        }
        return apiResult;
    }
}
