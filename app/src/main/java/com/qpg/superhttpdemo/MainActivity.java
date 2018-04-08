package com.qpg.superhttpdemo;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qpg.superhttp.SuperHttp;
import com.qpg.superhttp.callback.LoadingViewCallBack;
import com.qpg.superhttp.callback.ProgressDialogCallBack;
import com.qpg.superhttp.callback.SimpleCallBack;
import com.qpg.superhttp.callback.UCallback;
import com.qpg.superhttp.cookie.CookieJarImpl;
import com.qpg.superhttp.cookie.store.SPCookieStore;
import com.qpg.superhttp.interceptor.HttpLogInterceptor;
import com.qpg.superhttp.interceptor.NoCacheInterceptor;
import com.qpg.superhttp.interf.ILoader;
import com.qpg.superhttp.mode.DownProgress;
import com.qpg.superhttp.subscriber.IProgressDialog;
import com.qpg.superhttp.utils.HttpsUtils;

import java.io.File;
import java.lang.reflect.Type;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.ConnectionPool;
import okhttp3.Request;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mGet1,mGet2,mPost1,mPost2,mPost3,mParsr,mUpload,mDownload,mCustomLoad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        mGet1=findViewById(R.id.btn_get1);
        mGet2=findViewById(R.id.btn_get2);
        mPost1=findViewById(R.id.btn_post1);
        mPost2=findViewById(R.id.btn_post2);
        mPost3=findViewById(R.id.btn_post3);
        mParsr=findViewById(R.id.btn_parse);
        mUpload=findViewById(R.id.btn_up);
        mDownload=findViewById(R.id.btn_download);
        mCustomLoad=findViewById(R.id.btn_custom_load);

        mGet1.setOnClickListener(this);
        mGet2.setOnClickListener(this);
        mPost1.setOnClickListener(this);
        mPost2.setOnClickListener(this);
        mPost3.setOnClickListener(this);
        mParsr.setOnClickListener(this);
        mUpload.setOnClickListener(this);
        mDownload.setOnClickListener(this);
        mCustomLoad.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_get1:
                SuperHttp.get("v2/accept/user/getUserInfo")
                        .addParam("userid","4556")
                        .request(new ProgressDialogCallBack<String>(mProgressDialog,"用户信息获取失败，请刷新重试") {
                            @Override
                            public void onSuccess(String data) {

                            }
                        });
                break;
            case R.id.btn_get2:
                SuperHttp.get("v2/accept/user/getUserInfo")
                        .addParam("userid","4556")
                        .request(new SimpleCallBack<UserBean>() {
                            @Override
                            public void onSuccess(UserBean data) {

                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {

                            }
                        });
                break;
            case R.id.btn_post1:
                SuperHttp.post("user/login")
                        .addParam("userid","4556")
                        .request(new ProgressDialogCallBack<String>(mProgressDialog,"用户信息获取失败，请刷新重试") {
                            @Override
                            public void onSuccess(String data) {

                            }
                        });
                break;
            case R.id.btn_post2:
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
                break;
            case R.id.btn_post3:
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
                break;
                case R.id.btn_parse:
                    Type type=new TypeToken<UserBean>() {
                    }.getType();

                    SuperHttp.post("user/login")
                            .addCustomParse(new CustomApiFunc(type))
                            .request(new ProgressDialogCallBack<UserBean>(mProgressDialog,"用户信息获取失败，请刷新重试") {
                                @Override
                                public void onSuccess(UserBean data) {

                                }
                            });
                break;
            case R.id.btn_up:

                SuperHttp.upload("asd", new UCallback() {
                    @Override
                    public void onProgress(long currentLength, long totalLength, float percent) {

                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                    }
                }).addFile("avatar", new File(""))
                        .baseUrl("http://www.baidu.com")
                        .request(new SimpleCallBack<Object>() {
                            @Override
                            public void onSuccess(Object data) {
                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {
                            }
                        });
                break;
            case R.id.btn_download:

                SuperHttp.download("updateApp")
                        .setFileName("app.apk")
                        .request(new SimpleCallBack<DownProgress>() {
                            @Override
                            public void onSuccess(DownProgress downProgress) {

                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {
                            }
                        });

                break;
            case R.id.btn_custom_load:
                SuperHttp.get("v2/accept/user/getUserInfo")
                        .addParam("userid","4556")
                        .request(new LoadingViewCallBack<String>(iLoader) {
                            @Override
                            public void onSuccess(String data) {

                            }
                        });
                break;
                default:
        }
    }

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
    private IProgressDialog mProgressDialog = new IProgressDialog() {
        @Override
        public Dialog getDialog() {
            ProgressDialog dialog = new ProgressDialog(MainActivity.this);
            dialog.setMessage("请稍候...");
            return dialog;
        }
    };
    private void init(){
        SuperHttp.init(this.getApplication());
        SuperHttp.config()
                //配置请求主机地址
                .setBaseUrl("http://www.baidu.com")
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
        .postGzipInterceptor()
        //配置应用级拦截器
        .setInterceptor(new HttpLogInterceptor().setLevel(HttpLogInterceptor.Level.BODY))
        //配置网络拦截器
        .networkInterceptor(new NoCacheInterceptor())
        //配置转换工厂
        .setConverterFactory(GsonConverterFactory.create())
        //配置适配器工厂
        .callAdapterFactory(RxJava2CallAdapterFactory.create())
        //配置请求工厂
        .setCallFactory(new Call.Factory() {
            @Override
            public Call newCall(Request request) {
                return null;
            }
        })
        //配置连接池
       .connectionPool(new ConnectionPool())
        //配置主机证书验证
        .hostnameVerifier( new HttpsUtils.UnSafeHostnameVerifier("http://192.168.1.100/"))
        //配置SSL证书验证
        .setSSLSocketFactory(HttpsUtils.getSslSocketFactory().sSLSocketFactory);
        //配置主机代理
//        .setProxy(new Proxy(Proxy.Type.HTTP, new SocketAddress() {}));

    }

}
