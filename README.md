# SuperHttp

把RxJava2+Retrofit2进行再次封装，支持链式调用，方便调用。

- 项目地址：[https://github.com/qiaodashaoye/SuperHttp.git](https://github.com/qiaodashaoye/SuperHttp.git)

- 项目依赖：`compile 'com.qpg:superhttp:1.0.1'`

#### 眼前一亮的地方

- 链式调用，代码可读性高
- 动态切换请求基地址，人性化使用
- 自定义数据解析器，解决不同项目返回数据格式不同的问题

### 调用实例：

第一步需要在application中进行全局初始化以及添加全局相关配置，具体使用如下：
```
  SuperHttp.init(this);
  SuperHttp.config()
          //配置请求主机地址
          .setBaseUrl("http://11.11.1.1.com")
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
//        .setHttpCacheDirectory(new File(SuperHttp.getContext().getCacheDir(), ViseConfig.CACHE_HTTP_DIR))
          //配置自定义OkHttp缓存
//        .httpCache(new Cache(new File(SuperHttp.getContext().getCacheDir(), ViseConfig.CACHE_HTTP_DIR), ViseConfig.CACHE_MAX_SIZE))
          //配置自定义离线缓存
//        .cacheOffline(new Cache(new File(SuperHttp.getContext().getCacheDir(), ViseConfig.CACHE_HTTP_DIR), ViseConfig.CACHE_MAX_SIZE))
          //配置自定义在线缓存
//        .cacheOnline(new Cache(new File(SuperHttp.getContext().getCacheDir(), ViseConfig.CACHE_HTTP_DIR), ViseConfig.CACHE_MAX_SIZE))
          //配置开启Gzip请求方式，需要服务器支持
//        .postGzipInterceptor()
          //配置应用级拦截器
//        .setInterceptor(new HttpLogInterceptor().setLevel(HttpLogInterceptor.Level.BODY))
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
//        .hostnameVerifier(new SSLUtil.UnSafeHostnameVerifier("http://11.1.11.11/"))
          //配置SSL证书验证
//        .setSSLSocketFactory(SSLUtil.getSslSocketFactory(null, null, null))
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
 SuperHttp.upload("asd", new UCallback() {
                    @Override
                    public void onProgress(long currentLength, long totalLength, float percent) {

                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                    }
                }).addFile("avatar", new File(""))
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

####下载
```
   SuperHttp.download("app.apk")
                   .baseUrl("http://111.11.11.1/")
                   .setFileName("dasd")
                   .request(new SimpleCallBack<DownProgress>() {
                       @Override
                       public void onSuccess(DownProgress downProgress) {
                       }

                       @Override
                       public void onFail(int errCode, String errMsg) {
                       }
                   });

           break;
```
## 混淆
```java
#retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-dontnote retrofit2.Platform
-dontnote retrofit2.Platform$IOS$MainThreadExecutor
-dontwarn retrofit2.Platform$Java8
-keepattributes Signature
-keepattributes Exceptions
#okhttp
-dontwarn okhttp3.**
-keep class okhttp3.** { *;}
-dontwarn okio.**
# Gson
-keep class com.google.gson.stream.** { *; }
-keepattributes EnclosingMethod
-keep class org.xz_sale.entity.**{*;}
-keep class com.google.gson.** {*;}
-keep class com.google.**{*;}
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
-keep class com.google.gson.examples.android.model.** { *; }

```

## 交流方式
 * QQ: 1451449315
 * QQ群: 122619758