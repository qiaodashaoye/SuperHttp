package com.qpg.superhttp.callback;

import android.widget.Toast;
import com.qpg.superhttp.SuperHttp;
import com.qpg.superhttp.interf.ILoader;
import com.qpg.superhttp.subscriber.IProgressDialog;
import com.qpg.superhttp.subscriber.ProgressCancelListener;
import com.qpg.superhttp.utils.CommonUtil;
import io.reactivex.disposables.Disposable;

/**
 * <p>描述：可以自定义带有加载进度框的回调</p>
 * 1.可以自定义带有加载进度框的回调,是否需要显示，是否可以取消<br>
 * 2.取消对话框会自动取消掉网络请求<br>
 */
public abstract class LoadingViewCallBack<T> extends BaseCallback<T> implements ProgressCancelListener {
    private ILoader iLoader;
    private String onFailMsg;

    public LoadingViewCallBack(ILoader iLoader) {
        this.iLoader = iLoader;

    }
    public LoadingViewCallBack(ILoader iLoader, String onFailMsg) {
        this.iLoader = iLoader;
        this.onFailMsg = onFailMsg;
    }

    @Override
    public void onStart() {
        if(!CommonUtil.isConnected(SuperHttp.getContext())){
            Toast.makeText(SuperHttp.getContext(),"网络连接错误",Toast.LENGTH_SHORT).show();
            onFail(0,"");
        }else {
            iLoader.showLoader();
        }

    }

    @Override
    public void onFail(int errCode, String errMsg) {
        if(!CommonUtil.isConnected(SuperHttp.getContext())){
            return;
        }
        if(onFailMsg!=null){
            Toast.makeText(SuperHttp.getContext(),onFailMsg,Toast.LENGTH_SHORT).show();
        }

        iLoader.hideLoader();
    }

    @Override
    public void onCompleted() {
        iLoader.hideLoader();
    }


    @Override
    public void onCancelProgress() {
        if (disposed != null && !disposed.isDisposed()) {
            disposed.dispose();
        }
    }

    private Disposable disposed;

    public void subscription(Disposable disposed) {
        this.disposed = disposed;
    }
}
