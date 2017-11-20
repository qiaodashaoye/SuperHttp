package com.qpg.superhttp.config;

import com.qpg.superhttp.SuperHttp;
import com.qpg.superhttp.common.SuperConfig;
import com.qpg.superhttp.cookie.CookieJarImpl;
import com.qpg.superhttp.interceptor.GzipRequestInterceptor;
import com.qpg.superhttp.interceptor.OfflineCacheInterceptor;
import com.qpg.superhttp.interceptor.OnlineCacheInterceptor;
import com.qpg.superhttp.mode.ApiHost;

import java.io.File;
import java.net.Proxy;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.Call.Factory;
import okhttp3.ConnectionPool;
import okhttp3.Interceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;

/**
 * @author Administrator
 * @Description: 请求全局配置
 */
public class HttpGlobalConfig {
    //Call适配器工厂
    private CallAdapter.Factory callAdapterFactory;
    //转换工厂
    private Converter.Factory converterFactory;
    //Call工厂
    private Factory callFactory;
    //SSL工厂
    private SSLSocketFactory sslSocketFactory;
    public X509TrustManager trustManager;
    //主机域名验证
    private HostnameVerifier hostnameVerifier;
    //连接池
    private ConnectionPool connectionPool;
    //请求参数
    private Map<String, String> globalParams = new LinkedHashMap<>();
    //请求头
    private Map<String, String> globalHeaders = new LinkedHashMap<>();
    //是否使用Http缓存
    private boolean isHttpCache;
    //Http缓存路径
    private File httpCacheDirectory;
    //Http缓存对象
    private Cache httpCache;
    //是否使用Cookie
    private boolean isCookie;
    //Cookie配置
    private CookieJarImpl apiCookie;
    //基础域名
    private String baseUrl;
    //请求失败重试间隔时间
    private int retryDelayMillis;
    //请求失败重试次数
    private int retryCount;

 //   private static HttpGlobalConfig instance;
    private HttpGlobalConfig() {
    }

//    public static HttpGlobalConfig getInstance() {
//        if (instance == null) {
//            synchronized (HttpGlobalConfig.class) {
//                if (instance == null) {
//                    instance = new HttpGlobalConfig();
//                }
//            }
//        }
//        return instance;
//    }
    private static class HttpGlobalConfigHolder{
        private static final HttpGlobalConfig INSTANCE = new HttpGlobalConfig();
    }

    public static final HttpGlobalConfig getInstance() {
        return HttpGlobalConfigHolder.INSTANCE;
    }
    /**
     * 设置CallAdapter工厂
     *
     * @param factory
     * @return
     */
    public HttpGlobalConfig callAdapterFactory(CallAdapter.Factory factory) {
        this.callAdapterFactory = factory;
        return this;
    }

    /**
     * 设置转换工厂
     *
     * @param factory
     * @return
     */
    public HttpGlobalConfig setConverterFactory(Converter.Factory factory) {
        this.converterFactory = factory;
        return this;
    }

    /**
     * 设置Call的工厂
     *
     * @param factory
     * @return
     */
    public HttpGlobalConfig setCallFactory(Factory factory) {
        this.callFactory = checkNotNull(factory, "factory == null");
        return this;
    }

    /**
     * 设置SSL工厂
     *
     * @param sslSocketFactory
     * @return
     */
    public HttpGlobalConfig setSSLSocketFactory(SSLSocketFactory sslSocketFactory) {
        this.sslSocketFactory = sslSocketFactory;
        return this;
    }
    /**
     * 设置SSL工厂
     *
     * @param trustManager
     * @return
     */
    public HttpGlobalConfig setX509TrustManager(X509TrustManager trustManager) {
        this.trustManager = trustManager;
        return this;
    }

    /**
     * 设置主机验证机制
     *
     * @param hostnameVerifier
     * @return
     */
    public HttpGlobalConfig hostnameVerifier(HostnameVerifier hostnameVerifier) {
        this.hostnameVerifier = hostnameVerifier;
        return this;
    }

    /**
     * 设置连接池
     *
     * @param connectionPool
     * @return
     */
    public HttpGlobalConfig connectionPool(ConnectionPool connectionPool) {
        this.connectionPool = checkNotNull(connectionPool, "connectionPool == null");
        return this;
    }

    /**
     * 设置请求头部
     *
     * @param globalHeaders
     * @return
     */
    public HttpGlobalConfig globalHeaders(Map<String, String> globalHeaders) {
        if (globalHeaders != null) {
            this.globalHeaders = globalHeaders;
        }
        return this;
    }

    /**
     * 设置请求参数
     *
     * @param globalParams
     * @return
     */
    public HttpGlobalConfig globalParams(Map<String, String> globalParams) {
        if (globalParams != null) {
            this.globalParams = globalParams;
        }
        return this;
    }

    /**
     * 设置是否添加HTTP缓存
     *
     * @param isHttpCache
     * @return
     */
    public HttpGlobalConfig setHttpCache(boolean isHttpCache) {
        this.isHttpCache = isHttpCache;
        return this;
    }

    /**
     * 设置HTTP缓存路径
     * @param httpCacheDirectory
     * @return
     */
    public HttpGlobalConfig setHttpCacheDirectory(File httpCacheDirectory) {
        this.httpCacheDirectory = httpCacheDirectory;
        return this;
    }

    /**
     * 设置HTTP缓存
     * @param httpCache
     * @return
     */
    public HttpGlobalConfig httpCache(Cache httpCache) {
        this.httpCache = httpCache;
        return this;
    }

    /**
     * 设置是否添加Cookie
     *
     * @param isCookie
     * @return
     */
    public HttpGlobalConfig isUseCookie(boolean isCookie) {
        this.isCookie = isCookie;
        return this;
    }

    /**
     * 设置Cookie管理
     *
     * @param cookie
     * @return
     */
    public HttpGlobalConfig setCookie(CookieJarImpl cookie) {
        this.apiCookie = checkNotNull(cookie, "cookieManager == null");
        return this;
    }

    /**
     * 设置请求baseUrl
     *
     * @param baseUrl
     * @return
     */
    public HttpGlobalConfig setBaseUrl(String baseUrl) {
        this.baseUrl = checkNotNull(baseUrl, "baseUrl == null");
        ApiHost.setHost(this.baseUrl);
        return this;
    }

    /**
     * 设置请求失败重试间隔时间
     * @param retryDelayMillis
     * @return
     */
    public HttpGlobalConfig setRetryDelayMillis(int retryDelayMillis) {
        this.retryDelayMillis = retryDelayMillis;
        return this;
    }

    /**
     * 设置请求失败重试次数
     * @param retryCount
     * @return
     */
    public HttpGlobalConfig setRetryCount(int retryCount) {
        this.retryCount = retryCount;
        return this;
    }

    /**
     * 设置代理
     *
     * @param proxy
     * @return
     */
    public HttpGlobalConfig setProxy(Proxy proxy) {
        SuperHttp.getOkHttpBuilder().proxy(checkNotNull(proxy, "proxy == null"));
        return this;
    }

    /**
     * 设置连接超时时间（秒）
     *
     * @param timeout
     * @return
     */
    public HttpGlobalConfig setConnectTimeout(int timeout) {
        return setConnectTimeout(timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置读取超时时间（秒）
     *
     * @param timeout
     * @return
     */
    public HttpGlobalConfig setReadTimeout(int timeout) {
        return setReadTimeout(timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置写入超时时间（秒）
     *
     * @param timeout
     * @return
     */
    public HttpGlobalConfig setWriteTimeout(int timeout) {
        return setWriteTimeout(timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置连接超时时间
     *
     * @param timeout
     * @param unit
     * @return
     */
    public HttpGlobalConfig setConnectTimeout(int timeout, TimeUnit unit) {
        if (timeout > -1) {
            SuperHttp.getOkHttpBuilder().connectTimeout(timeout, unit);
        } else {
            SuperHttp.getOkHttpBuilder().connectTimeout(SuperConfig.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        }
        return this;
    }

    /**
     * 设置写入超时时间
     *
     * @param timeout
     * @param unit
     * @return
     */
    public HttpGlobalConfig setWriteTimeout(int timeout, TimeUnit unit) {
        if (timeout > -1) {
            SuperHttp.getOkHttpBuilder().writeTimeout(timeout, unit);
        } else {
            SuperHttp.getOkHttpBuilder().writeTimeout(SuperConfig.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        }
        return this;
    }

    /**
     * 设置读取超时时间
     *
     * @param timeout
     * @param unit
     * @return
     */
    public HttpGlobalConfig setReadTimeout(int timeout, TimeUnit unit) {
        if (timeout > -1) {
            SuperHttp.getOkHttpBuilder().readTimeout(timeout, unit);
        } else {
            SuperHttp.getOkHttpBuilder().readTimeout(SuperConfig.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        }
        return this;
    }

    /**
     * 设置拦截器
     *
     * @param interceptor
     * @return
     */
    public HttpGlobalConfig setInterceptor(Interceptor interceptor) {
        SuperHttp.getOkHttpBuilder().addInterceptor(checkNotNull(interceptor, "interceptor == null"));
        return this;
    }

    /**
     * 设置网络拦截器
     *
     * @param interceptor
     * @return
     */
    public HttpGlobalConfig networkInterceptor(Interceptor interceptor) {
        SuperHttp.getOkHttpBuilder().addNetworkInterceptor(checkNotNull(interceptor, "interceptor == null"));
        return this;
    }

    /**
     * 使用POST方式是否需要进行GZIP压缩，服务器不支持则不设置
     *
     * @return
     */
    public HttpGlobalConfig postGzipInterceptor() {
        setInterceptor(new GzipRequestInterceptor());
        return this;
    }

    /**
     * 设置在线缓存，主要针对网路请求过程进行缓存
     *
     * @param httpCache
     * @return
     */
    public HttpGlobalConfig cacheOnline(Cache httpCache) {
        networkInterceptor(new OnlineCacheInterceptor());
        this.httpCache = httpCache;
        return this;
    }

    /**
     * 设置在线缓存，主要针对网路请求过程进行缓存
     *
     * @param httpCache
     * @param cacheControlValue
     * @return
     */
    public HttpGlobalConfig cacheOnline(Cache httpCache, final int cacheControlValue) {
        networkInterceptor(new OnlineCacheInterceptor(cacheControlValue));
        this.httpCache = httpCache;
        return this;
    }

    /**
     * 设置离线缓存，主要针对网路请求过程进行缓存
     *
     * @param httpCache
     * @return
     */
    public HttpGlobalConfig cacheOffline(Cache httpCache) {
        networkInterceptor(new OfflineCacheInterceptor(SuperHttp.getContext()));
        setInterceptor(new OfflineCacheInterceptor(SuperHttp.getContext()));
        this.httpCache = httpCache;
        return this;
    }

    /**
     * 设置离线缓存，主要针对网路请求过程进行缓存
     *
     * @param httpCache
     * @param cacheControlValue
     * @return
     */
    public HttpGlobalConfig cacheOffline(Cache httpCache, final int cacheControlValue) {
        networkInterceptor(new OfflineCacheInterceptor(SuperHttp.getContext(), cacheControlValue));
        setInterceptor(new OfflineCacheInterceptor(SuperHttp.getContext(), cacheControlValue));
        this.httpCache = httpCache;
        return this;
    }

    public CallAdapter.Factory getCallAdapterFactory() {
        return callAdapterFactory;
    }

    public Converter.Factory getConverterFactory() {
        return converterFactory;
    }

    public Factory getCallFactory() {
        return callFactory;
    }

    public SSLSocketFactory getSslSocketFactory() {
        return sslSocketFactory;
    }
    public X509TrustManager getX509TrustManager() {
        return trustManager;
    }

    public HostnameVerifier getHostnameVerifier() {
        return hostnameVerifier;
    }

    public ConnectionPool getConnectionPool() {
        return connectionPool;
    }

    public Map<String, String> getGlobalParams() {
        return globalParams;
    }

    public Map<String, String> getGlobalHeaders() {
        return globalHeaders;
    }

    public boolean isHttpCache() {
        return isHttpCache;
    }

    public boolean isCookie() {
        return isCookie;
    }

    public CookieJarImpl getApiCookie() {
        return apiCookie;
    }

    public Cache getHttpCache() {
        return httpCache;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public int getRetryDelayMillis() {
        if (retryDelayMillis <= 0) {
            retryDelayMillis = SuperConfig.DEFAULT_RETRY_DELAY_MILLIS;
        }
        return retryDelayMillis;
    }

    public int getRetryCount() {
        if (retryCount <= 0) {
            retryCount = SuperConfig.DEFAULT_RETRY_COUNT;
        }
        return retryCount;
    }

    public File getHttpCacheDirectory() {
        return httpCacheDirectory;
    }

    private <T> T checkNotNull(T t, String message) {
        if (t == null) {
            throw new NullPointerException(message);
        }
        return t;
    }
}
