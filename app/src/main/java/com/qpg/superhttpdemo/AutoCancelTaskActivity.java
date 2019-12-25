package com.qpg.superhttpdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.qpg.superhttp.SuperHttp;
import com.qpg.superhttp.callback.SimpleCallBack;

public class AutoCancelTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_cancel_task);
        SuperHttp.get("getUserInfo")
                .setRetryCount(0)
                .setReadTimeOut(5)
                .setWriteTimeOut(5)
                .setConnectTimeOut(5)
                .lifeCycleOwner(this)
                .baseUrl("http://192.168.1.79:5830/")
                .request(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String data) {
                        System.out.println("1-------->"+"onSuccess");
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                        System.out.println("1-------->"+"onFail");
                    }
                });
        SuperHttp.post("getUserInfo")
                .setRetryCount(0)
                .setReadTimeOut(5)
                .setWriteTimeOut(5)
                .setConnectTimeOut(5)
                .lifeCycleOwner(this)
                .baseUrl("http://192.168.1.79:5830/")
                .request(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String data) {
                        System.out.println("1-------->"+"onSuccess");
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                        System.out.println("1-------->"+"onFail");
                    }
                });
        SuperHttp.get("getUserInfo")
                .lifeCycleOwner(this)
                .baseUrl("http://192.168.1.79:5830/")
                .request(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String data) {
                        System.out.println("1-------->"+"onSuccess");
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                        System.out.println("1-------->"+"onFail");
                    }
                });
        SuperHttp.post("getUserInfo")
                .setRetryCount(0)
                .setReadTimeOut(5)
                .setWriteTimeOut(5)
                .setConnectTimeOut(5)
                .lifeCycleOwner(this)
                .baseUrl("http://192.168.1.79:5830/")
                .request(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String data) {
                        System.out.println("1-------->"+"onSuccess");
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                        System.out.println("1-------->"+"onFail");
                    }
                });
        SuperHttp.get("getUserInfo")
                .setRetryCount(0)
                .setReadTimeOut(5)
                .setWriteTimeOut(5)
                .setConnectTimeOut(5)
                .lifeCycleOwner(this)
                .baseUrl("http://192.168.1.79:5830/")
                .request(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String data) {
                        System.out.println("1-------->"+"onSuccess");
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                        System.out.println("1-------->"+"onFail");
                    }
                });

    }
}
