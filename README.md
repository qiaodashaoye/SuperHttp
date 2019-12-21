# SuperHttp

把RxJava2+Retrofit2进行再次封装，支持链式调用，方便调用。

- 项目地址：[https://github.com/qiaodashaoye/SuperHttp.git](https://github.com/qiaodashaoye/SuperHttp.git)

- 项目依赖：`implementation 'com.qpg.net:superhttp:1.1.1'`

#### 眼前一亮的地方

- 链式调用，代码可读性高
- 动态切换请求基地址，人性化使用
- 生命周期感知能力，跟内存泄漏说拜拜
- 自定义数据解析器，解决不同项目返回数据格式不同的问题

### 调用实例：

第一步需要在application中进行全局初始化以及添加全局相关配置，具体使用如下：
> 简单初始化，使用默认配置，方便快捷
```
SuperHttp.init(this);
SuperHttp.config()
        //配置请求主机地址
        .setBaseUrl("服务器地址");
        
```
> 详细初始化,自定义配置
```

    //动态头部拦截器，可以用于添加token类似这种动态的头部,当然，也可以放普通请求头数据
    HeadersInterceptorDynamic headersInterceptor=new HeadersInterceptorDynamic(new HeadersInterceptorDynamic.Headers() {
            @Override
            public Map<String, String> headers() {

                HashMap<String,String> headers=new HashMap<>();
                headers.put("token","从本地缓存中获取的token值");
                return headers;
            }
        });
        
        //普通请求头拦截器
         Map<String, String> headers=new HashMap<>();
        headers.put("version","2.0");
        headers.put("systemType","android");
        HeadersInterceptorNormal headersInterceptorNormal=new HeadersInterceptorNormal(headers);
        
 HttpLogInterceptor httpLogInterceptor=new HttpLogInterceptor("-------->");
        httpLogInterceptor.setLevel(HttpLogInterceptor.Level.BODY);
  SuperHttp.init(this);
  SuperHttp.config()
          //配置请求主机地址
          .setBaseUrl("http://11.11.1.1.com/")
          //配置全局请求头
          .globalHeaders(new HashMap<String, String>())
          //配置全局请求参数
          .globalParams(new HashMap<String, String>())
          //配置读取超时时间，单位秒
          .setReadTimeout(30)
          //配置写入超时时间，单位秒
          .setWriteTimeout(30)
          //配置连接超时时间，单位秒
          .setConnectTimeout(30)
          //配置请求失败重试次数
          .setRetryCount(3)
          //配置请求失败重试间隔时间，单位毫秒
          .setRetryDelayMillis(1000)
          //配置是否使用cookie
          .isUseCookie(true)
          //配置自定义cookie
          .setCookie(new CookieJarImpl(new SPCookieStore(this)));
          //配置是否使用OkHttp的默认缓存
//        .setHttpCache(true)
          //配置OkHttp缓存路径
//        .setHttpCacheDirectory(new File(SuperHttp.getContext().getCacheDir(), SuperConfig.CACHE_HTTP_DIR))
          //配置自定义OkHttp缓存
//        .httpCache(new Cache(new File(SuperHttp.getContext().getCacheDir(), SuperConfig.CACHE_HTTP_DIR), SuperConfig.CACHE_MAX_SIZE))
          //配置自定义离线缓存
//        .cacheOffline(new Cache(new File(SuperHttp.getContext().getCacheDir(), SuperConfig.CACHE_HTTP_DIR), SuperConfig.CACHE_MAX_SIZE))
          //配置自定义在线缓存
//        .cacheOnline(new Cache(new File(SuperHttp.getContext().getCacheDir(), SuperConfig.CACHE_HTTP_DIR), SuperConfig.CACHE_MAX_SIZE))
          //配置开启Gzip请求方式，需要服务器支持
//        .postGzipInterceptor()
          //配置应用级拦截器
//        .addInterceptor(httpLogInterceptor)
          //配置网络拦截器
//        .networkInterceptor(new NoCacheInterceptor())
          //配置转换工厂
//        .setConverterFactory(GsonConverterFactory.create())
          //配置适配器工厂
//        .callAdapterFactory(RxJava2CallAdapterFactory.create())
          //配置请求工厂
//        .setCallFactory(new Call.Factory() {
//            @Override
//            public Call newCall(Request request) {
//                return null;
//            }
//        })
          //配置连接池
//        .connectionPool(new ConnectionPool())
          //配置主机证书验证
//        .hostnameVerifier(HttpsUtils.UnSafeHostnameVerifier)
          //配置SSL证书验证
//        .setSSLSocketFactory(HttpsUtils.getSslSocketFactory().sSLSocketFactory)
         //配置主机代理
//        .setProxy(new Proxy(Proxy.Type.HTTP, new SocketAddress() {}));

```
#### Get方式
- get方式一（推荐）
此方式需要实现ProgressDialogCallBack，第一个参数是为了方便使用者传入自定义的Dialog加载框，
第二个参数是传入回调失败后的提示信息，使用者不必手动重写onFail()方法，使用非常简单,具体
使用方法请看实例。
```
 SuperHttp.get("user/getUserInfo")
                        .lifeCycleOwner(this) //绑定生命周期拥有者，方便在界面销毁时自动取消网络请求，也可通过tag属性，手动取消。
                        .addParam("userid","4556")
                        .request(new ProgressDialogCallBack<String>(mProgressDialog,"用户信息获取失败，请刷新重试") {
                            @Override
                            public void onSuccess(String data) {

                            }
                        });
```
- get方式二
```
  SuperHttp.get("user/getUserInfo")
                        .addParam("userid","4556")
                        .request(new SimpleCallBack<UserBean>() {
                            @Override
                            public void onSuccess(UserBean data) {
                                
                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {

                            }
                        });
```                      
#### POST方式

 - post表单
 ```
 SuperHttp.post("user/login")
                         .addParam("userid","4556")
                         .request(new ProgressDialogCallBack<String>(mProgressDialog,"用户信息获取失败，请刷新重试") {
                             @Override
                             public void onSuccess(String data) {
 
                             }
                         });
 ```
 - post Map

```
  HashMap<String,String> map=new HashMap<>();
                map.put("key1","13213");
                map.put("key2","5456");
                SuperHttp.post("user/login")
                        .addParams(map)
                        .request(new ProgressDialogCallBack<String>(mProgressDialog,"用户信息获取失败，请刷新重试") {
                            @Override
                            public void onSuccess(String data) {

                            }
                        });
```

 - post Json

```
   UserBean userBean=new UserBean();
                  userBean.setAvatarUrl("http://www.ae.com/asd.png");
                  userBean.setUserName("小和尚");
                  SuperHttp.post("user/register")
                          .setJson(new Gson().toJson(userBean))
                          .request(new ProgressDialogCallBack<String>(mProgressDialog,"用户信息获取失败，请刷新重试") {
                              @Override
                              public void onSuccess(String data) {
  
                              }
                          });
```
#### 自定义解析器
```
 Type type=new TypeToken<UserBean>() {
                    }.getType();

 SuperHttp.post("user/login")
         .addCustomParse(new CustomApiFunc(type))
         .request(new ProgressDialogCallBack<UserBean>(mProgressDialog,"用户信息获取失败，请刷新重试") {
             @Override
             public void onSuccess(UserBean data) {

             }
         });
```
#### 上传
```
 SuperHttp.upload("user/updateAvatar")
                            .addImageFile("head",new File("path"))
                            .request();
```
```
 SuperHttp.upload("asd", new UCallback() {
                    @Override
                    public void onProgress(long currentLength, long totalLength, float percent) {

                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                    }
                }).addFile("file", new File(""))
                        .baseUrl("https://111.111.1.11/")
                        .request(new SimpleCallBack<Object>() {
                            @Override
                            public void onSuccess(Object data) {
                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {
                            }
                        });
```

#### 下载
```
 SuperHttp.download("profile/2019-12-03/237349310374eb0c4e56795cfa20b37a.apk")
                .baseUrl("http://www.baidu.com/")
                .setFileName("app.apk")
              //.addParam("filename","app.apk")
                .request(new SimpleCallBack<DownProgress>() {
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        progressDialog.dismiss();

                    }

                    @Override
                    public void onSuccess(DownProgress downProgress) {
                        System.out.println("1-------->"+downProgress);

                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                    }
                });
```
#### 自定义加载框
```
  SuperHttp.get("v2/accept/user/getUserInfo")
                  .addParam("userid","4556")
                  .request(new LoadingViewCallBack<String>(iLoader) {
                      @Override
                      public void onSuccess(String data) {

                      }
                  });
                  
                  
  private ILoader iLoader=new ILoader() {
         //在此实例化自己自定义的加载框（一般为自定义的View）
         @Override
         public void showLoader() {
             //在此调用加载框的显示方法
         }
  
         @Override
         public void hideLoader() {
             //在此调用加载框的隐藏方法
         }
     };
```

#### 内部错误码定义说明
```
        //未知错误
        public static final int UNKNOWN = 1000;
        //解析错误
        public static final int PARSE_ERROR = 1001;
        //网络错误
        public static final int NETWORK_ERROR = 1002;
        //协议出错
        public static final int HTTP_ERROR = 1003;
        //证书出错
        public static final int SSL_ERROR = 1005;
        //连接超时
        public static final int TIMEOUT_ERROR = 1006;
        //调用错误
        public static final int INVOKE_ERROR = 1007;
        //类转换错误
        public static final int CONVERT_ERROR = 1008;
```

## 混淆
``` java
#gson
-dontwarn com.google.gson.**
-keep class com.google.gson.** { *; }

#rxjava
-dontwarn io.reactivex.**
-keep class io.reactivex.** { *; }

#okhttp
-dontwarn okio.**
-keep class okio.** { *; }
-dontwarn okhttp3.**
-keep class okhttp3.** { *; }

#okio
-dontwarn okio.**
-keep class okio.**{*;}
-keep interface okio.**{*;}

#retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

#netexpand
-keep class com.qpg.superhttp.netexpand.mode.ApiResult { *; }
```

## 交流方式
 * QQ: 1451449315
 * QQ群: 122619758
 
 ## Licenses
 ```
  Copyright 2017 qpg
 
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at
 
       http://www.apache.org/licenses/LICENSE-2.0
 
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 ```