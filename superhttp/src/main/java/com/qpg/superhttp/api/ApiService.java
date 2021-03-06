package com.qpg.superhttp.api;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.HTTP;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.OPTIONS;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * @Description: 提供的系列接口
 */
public interface ApiService {
    @GET()
    Observable<ResponseBody> get(@Url String url, @QueryMap Map<String, String> maps);

    @FormUrlEncoded
    @POST()
    Observable<ResponseBody> post(@Url() String url, @FieldMap Map<String, String> maps);

    @FormUrlEncoded
    @POST()
    Observable<ResponseBody> postForm(@Url() String url, @FieldMap Map<String, Object> maps);

    @POST()
    Observable<ResponseBody> postBody(@Url() String url, @Body RequestBody requestBody);

    @HEAD()
    Observable<ResponseBody> head(@Url String url, @QueryMap Map<String, String> maps);

    @OPTIONS()
    Observable<ResponseBody> options(@Url String url, @QueryMap Map<String, String> maps);

    @FormUrlEncoded
    @PUT()
    Observable<ResponseBody> put(@Url() String url, @FieldMap Map<String, String> maps);

    @FormUrlEncoded
    @PATCH()
    Observable<ResponseBody> patch(@Url() String url, @FieldMap Map<String, String> maps);

    @PATCH()
    Observable<ResponseBody> patchBody(@Url() String url, @Body RequestBody requestBody);

    @DELETE()
    Observable<ResponseBody> delete(@Url() String url, @QueryMap Map<String, String> maps);

    //@DELETE()//delete body请求比较特殊 需要自定义
    @HTTP(method = "DELETE",/*path = "",*/hasBody = true)
    Observable<ResponseBody> deleteBody(@Url String url, @Body Object object);

    //@DELETE()//delete body请求比较特殊 需要自定义
    @HTTP(method = "DELETE",/*path = "",*/hasBody = true)
    Observable<ResponseBody> deleteBody(@Url String url, @Body RequestBody body);

    //@DELETE()//delete body请求比较特殊 需要自定义
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @HTTP(method = "DELETE",/*path = "",*/hasBody = true)
    Observable<ResponseBody> deleteJson(@Url String url, @Body RequestBody jsonBody);

    @Streaming
    @GET()
    Observable<ResponseBody> downFile(@Url() String url, @QueryMap Map<String, String> maps);

    @Multipart
    @POST()
    Observable<ResponseBody> uploadFiles(@Url() String url, @Part() List<MultipartBody.Part> parts);

}
