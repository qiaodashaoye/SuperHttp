package com.qpg.superhttp.callback;

import android.app.Dialog;
import android.content.DialogInterface;
import android.widget.Toast;

import com.qpg.superhttp.utils.CommonUtil;
import com.qpg.superhttp.subscriber.IProgressDialog;
import com.qpg.superhttp.subscriber.ProgressCancelListener;
import com.qpg.superhttp.SuperHttp;

import io.reactivex.disposables.Disposable;

/**
 * <p>描述：可以自定义带有加载进度框的回调</p>
 * 1.可以自定义带有加载进度框的回调,是否需要显示，是否可以取消<br>
 * 2.取消对话框会自动取消掉网络请求<br>
 */
public abstract class ProgressDialogCallBack<T> extends BaseCallback<T> implements ProgressCancelListener {
    private IProgressDialog progressDialog;
    private Dialog mDialog;
    private boolean isShowProgress = true;
    private String onFailMsg;

    public ProgressDialogCallBack(IProgressDialog progressDialog) {
        this.progressDialog = progressDialog;
        init(false);
    }
    public ProgressDialogCallBack(IProgressDialog progressDialog,String onFailMsg) {
        this.progressDialog = progressDialog;
        this.onFailMsg = onFailMsg;
        init(false);
    }
    /**
     * 自定义加载进度框,可以设置是否显示弹出框，是否可以取消
     *
     * @param progressDialog dialog
     * @param isShowProgress 是否显示进度
     * @param isCancel       对话框是否可以取消
     */
    public ProgressDialogCallBack(IProgressDialog progressDialog, boolean isShowProgress, boolean isCancel) {
        this.progressDialog = progressDialog;
        this.isShowProgress = isShowProgress;
        init(isCancel);
    }

    /**
     * 初始化
     *
     * @param isCancel
     */
    private void init(boolean isCancel) {
        if (progressDialog == null) {
            return;
        }
        mDialog = progressDialog.getDialog();
        if (mDialog == null) {
            return;
        }
        mDialog.setCancelable(isCancel);
        if (isCancel) {
            mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    ProgressDialogCallBack.this.onCancelProgress();
                }
            });
        }
    }

    /**
     * 展示进度框
     */
    private void showProgress() {
        if (!isShowProgress) {
            return;
        }
        if (mDialog != null) {
            if (!mDialog.isShowing()) {
                mDialog.show();
            }
        }
    }

    /**
     * 取消进度框
     */
    private void dismissProgress() {
        if (!isShowProgress) {
            return;
        }
        if (mDialog != null) {
            if (mDialog.isShowing()) {
                mDialog.dismiss();
            }
        }
    }

    @Override
    public void onStart() {
        if(!CommonUtil.isConnected(SuperHttp.getContext())){
            Toast.makeText(SuperHttp.getContext(),"网络连接错误",Toast.LENGTH_SHORT).show();
            onFail(0,"");
        }else {
            showProgress();
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
        dismissProgress();
    }

    @Override
    public void onCompleted() {
        dismissProgress();
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
