# SuperHttp

把RxJava2+Retrofit2进行再次封装，支持链式调用，方便调用。

- 项目地址：[https://github.com/qiaodashaoye/SuperHttp.git](https://github.com/qiaodashaoye/SuperHttp.git)

- 项目依赖：`compile 'com.qpg.superhttp:SuperHttp:1.0.0'`

### 调用实例：

第一步需要在application中进行全局初始化以及添加全局相关配置，具体使用如下：
```
  SuperHttp.init(this);
        SuperHttp.config()
                //配置请求主机地址
                .setBaseUrl("http://m.sanniujinfu.com")
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
                .setCookie(new CookieJarImpl(new SPCookieStore(this)))
                //配置是否使用OkHttp的默认缓存
//              .setHttpCache(true)
                //配置OkHttp缓存路径
//              .setHttpCacheDirectory(new File(SuperHttp.getContext().getCacheDir(), ViseConfig.CACHE_HTTP_DIR))
                //配置自定义OkHttp缓存
//              .httpCache(new Cache(new File(SuperHttp.getContext().getCacheDir(), ViseConfig.CACHE_HTTP_DIR), ViseConfig.CACHE_MAX_SIZE))
                //配置自定义离线缓存
//              .cacheOffline(new Cache(new File(SuperHttp.getContext().getCacheDir(), ViseConfig.CACHE_HTTP_DIR), ViseConfig.CACHE_MAX_SIZE))
                //配置自定义在线缓存
//              .cacheOnline(new Cache(new File(SuperHttp.getContext().getCacheDir(), ViseConfig.CACHE_HTTP_DIR), ViseConfig.CACHE_MAX_SIZE))
                //配置开启Gzip请求方式，需要服务器支持
//              .postGzipInterceptor()
                //配置应用级拦截器
//              .setInterceptor(new HttpLogInterceptor().setLevel(HttpLogInterceptor.Level.BODY))
                //配置网络拦截器
//              .networkInterceptor(new NoCacheInterceptor())
                //配置转换工厂
//              .setConverterFactory(GsonConverterFactory.create())
                //配置适配器工厂
//              .callAdapterFactory(RxJava2CallAdapterFactory.create())
                //配置请求工厂
//              .setCallFactory(new Call.Factory() {
//                  @Override
//                  public Call newCall(Request request) {
//                      return null;
//                  }
//              })
                //配置连接池
//              .connectionPool(new ConnectionPool())
                //配置主机证书验证
//              .hostnameVerifier(new SSLUtil.UnSafeHostnameVerifier("http://192.168.1.100/"))
                //配置SSL证书验证
//              .setSSLSocketFactory(SSLUtil.getSslSocketFactory(null, null, null))
               //配置主机代理
//              .setProxy(new Proxy(Proxy.Type.HTTP, new SocketAddress() {}));

```
- get方式
```
ViseHttp.GET("getAuthor").request(new ACallback<AuthorModel>() {
    @Override
    public void onSuccess(AuthorModel authorModel) {
    }

    @Override
    public void onFail(int errCode, String errMsg) {
    }
});
```
- post方式
```
SuperHttp.post("user/login")
         .addForm("UserName", "1wefwefwf")
         .addForm("Password", "sdfafadf")
         .request(new ProgressDialogCallBack<UserBean>(mProgressDialog, true, true) {
             @Override
             public void onSuccess(UserBean data) {

             }
         });
```

