package com.qpg.superhttpdemo;

import android.app.Dialog;
import android.app.ProgressDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import okhttp3.logging.HttpLoggingInterceptor;
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
                SuperHttp.post("thirdparty/qqevaluate")
                        .baseUrl("http://www.hg3-app.com/")
                        .addParam("qq","112413")
                        .request(new SimpleCallBack<UserBean>() {
                            @Override
                            public void onStart() {
                                super.onStart();
                            }

                            @Override
                            public void onCompleted() {
                                super.onCompleted();
                            }

                            @Override
                            public void onSuccess(UserBean data) {

                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {

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
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                //打印retrofit日志
                Log.i("------------->","retrofitBack = "+message);
            }
        });
        SuperHttp.init(this.getApplication());
        SuperHttp.config()
                .setInterceptor(loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY));
            //    .setInterceptor(new HttpLogInterceptor().setLevel(HttpLogInterceptor.Level.BODY));

    }

}
