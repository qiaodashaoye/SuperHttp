package com.qpg.superhttp.callback;

/**
 * <p>描述：简单的回调,默认可以使用该回调，不用关注其他回调方法</p>
 * 使用该回调默认只需要处理onError，onSuccess两个方法既成功失败<br>
 */
public abstract class StringCallBack extends BaseCallback<String> {

    @Override
    public void onStart() {
//        if(!CommonUtil.isConnected(SuperHttp.getContext())){
//            Toast.makeText(SuperHttp.getContext(),"网络连接错误",Toast.LENGTH_LONG).show();
//            onCompleted();
//        }
    }

    @Override
    public void onCompleted() {

    }
}
