package com.qpg.superhttp.lifecycle;

import android.app.Activity;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.qpg.superhttp.SuperHttp;

public class BaseLifeCycleObserver implements LifecycleObserver {

    private Activity mActivity;
    private Fragment mFragment;
    private  Lifecycle mLifecycle;
    private static final String TAG = "1-------->";
    public BaseLifeCycleObserver(Lifecycle lifecycle, Activity activity){
        mActivity=activity;
        mLifecycle=lifecycle;
        mActivity.getClass().getName();
    }
    public BaseLifeCycleObserver(Lifecycle lifecycle, Fragment fragment){
        mFragment=fragment;
        mLifecycle=lifecycle;
        mActivity.getClass().getName();
    }

    // 使用注解  @OnLifecycleEvent 来表明该方法需要监听指定的生命周期事件
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void connectListener() {
//        ...
        Log.d(TAG, "connectListener:  --------   onResume" );
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void disconnectListener() {
//        ...
        Log.d(TAG, "disconnectListener: -------   onPause");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void destroyListener() {
    //    mLifecycle.removeObserver(this);
        if(mActivity!=null){
            SuperHttp.cancelSome(mActivity.getClass().getName());
        }
        if(mFragment!=null){
            SuperHttp.cancelSome(mFragment.getClass().getName());
        }
//        ...
        Log.d(TAG, "destroyListener: -------   onDestroy");
    }
}
