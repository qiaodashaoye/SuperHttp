package com.qpg.superhttpdemo;

import android.app.Dialog;
import android.app.ProgressDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.qpg.superhttp.interceptor.HeadersInterceptorDynamic;
import com.qpg.superhttp.interceptor.HeadersInterceptorNormal;
import com.qpg.superhttp.interceptor.HttpLogInterceptor;
import com.qpg.superhttp.interf.ILoader;
import com.qpg.superhttp.mode.DownProgress;
import com.qpg.superhttp.subscriber.IProgressDialog;
import java.io.File;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mGet1,mGet2,mPost1,mPost2,mPost3,mParsr,mUpload,mDownload,mCustomLoad,btnAutoCancelTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Test(this);
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
        btnAutoCancelTask=findViewById(R.id.btn_auto_cancel_task);

        mGet1.setOnClickListener(this);
        mGet2.setOnClickListener(this);
        mPost1.setOnClickListener(this);
        mPost2.setOnClickListener(this);
        mPost3.setOnClickListener(this);
        mParsr.setOnClickListener(this);
        mUpload.setOnClickListener(this);
        mDownload.setOnClickListener(this);
        mCustomLoad.setOnClickListener(this);
        btnAutoCancelTask.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_get1:
                SuperHttp.cancelTag(123);
            /*    SuperHttp.get("v2/accept/user/getUserInfo")
                        .addParam("userid","4556")
                        .request(new ProgressDialogCallBack<String>(mProgressDialog,"用户信息获取失败，请刷新重试") {
                            @Override
                            public void onSuccess(String data) {

                            }
                        });*/
                break;
            case R.id.btn_get2:
                SuperHttp.get("v2/accept/user/getUserInfo")
                        .tag(22)
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
              /*  SuperHttp.upload("路径")
                        .baseUrl("")
                        .addFile("文件名", new File(""))
                        .addParam("额外参数", par)
                        .request(callBack);*/

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
              //  downLoad();
                mDownload();

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
                case R.id.btn_auto_cancel_task:
                    startActivity(new Intent(MainActivity.this,AutoCancelTaskActivity.class));
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
        HeadersInterceptorDynamic headersInterceptorDynamic=new HeadersInterceptorDynamic(new HeadersInterceptorDynamic.Headers() {
            @Override
            public Map<String, String> headers() {

                HashMap<String,String> headers=new HashMap<>();
                headers.put("token","从本地缓存中获取的token值");
                return headers;
            }
        });
        Map<String, String> headers=new HashMap<>();
        headers.put("version","2.0");
        headers.put("systemType","android");
        HeadersInterceptorNormal headersInterceptorNormal=new HeadersInterceptorNormal(headers);
        HttpLogInterceptor httpLogInterceptor=new HttpLogInterceptor("-------->");
        httpLogInterceptor.setLevel(HttpLogInterceptor.Level.BODY);
        SuperHttp.init(this.getApplication());
        SuperHttp.config()
                .setBaseUrl("http://www.baidu.com/")
                .setWriteTimeout(10)
                .setReadTimeout(10)
                .setConnectTimeout(10)
                .addInterceptor(httpLogInterceptor);

    }
    private void mDownload() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(1);
        progressDialog.setCancelable(true);
        //     progressDialog.incrementProgressBy(5);
        progressDialog.show();
        SuperHttp.download("home/zjbapp/profile/2019-12-03/237349310374eb0c4e56795cfa20b37a.apk")
                .baseUrl("https://www.baidu.com/")
                .tag(123)
                .setFileName("app.apk")
                //     .addParam("filename","app.apk")
                .request(new SimpleCallBack<DownProgress>() {
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        progressDialog.dismiss();

                    }

                    @Override
                    public void onSuccess(DownProgress downProgress) {
                        System.out.println("1-------->"+downProgress);
//                        int progress = Double.valueOf(downProgress.getPercent().substring(0, downProgress.getPercent().length() - 1) + "").intValue();
//                        progressDialog.setProgress(progress);
//                        if (downProgress.isDownComplete()) {
//                            progressDialog.dismiss();
//
//                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                    }
                });
    }


}
