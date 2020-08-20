package com.qpg.superhttp.request;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.qpg.superhttp.SuperHttp;
import com.qpg.superhttp.callback.BaseCallback;
import com.qpg.superhttp.config.SuperConfig;
import com.qpg.superhttp.core.ApiManager;
import com.qpg.superhttp.lifecycle.BaseLifeCycleObserver;
import com.qpg.superhttp.transformer.ApiRetryFunc;
import com.qpg.superhttp.mode.CacheResult;
import com.qpg.superhttp.subscriber.DownCallbackSubscriber;
import com.qpg.superhttp.transformer.DownloadFunction;

import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * @Description: 下载请求
 */
public class DownloadRequest extends BaseHttpRequest<DownloadRequest> {

    private String rootName;
    private String dirName = SuperConfig.DEFAULT_DOWNLOAD_DIR;
    private String fileName = SuperConfig.DEFAULT_DOWNLOAD_FILE_NAME;
    private int timeInterval = 1;
    private TimeUnit timeUnit=TimeUnit.SECONDS;

    public DownloadRequest(String suffixUrl) {
        super(suffixUrl);
        rootName = getDiskCachePath(SuperHttp.getContext());
    }

    /**
     * 设置根目录，默认App缓存目录，有外置卡则默认外置卡缓存目录
     * @param rootName
     * @return
     */
    public DownloadRequest setRootName(String rootName) {
        if (!TextUtils.isEmpty(rootName)) {
            this.rootName = rootName;
        }
        return this;
    }

    /**
     * 设置文件夹路径
     * @param dirName
     * @return
     */
    public DownloadRequest setDirName(String dirName) {
        if (!TextUtils.isEmpty(dirName)) {
            this.dirName = dirName;
        }
        return this;
    }
    /**
     * 设置进度监听时间间隔
     * @param timeInterval
     * @return
     */
    public DownloadRequest setTimeInterval(int timeInterval) {
        if(timeInterval>0){
            this.setTimeInterval(timeInterval,TimeUnit.SECONDS);
        }
        return this;
    }
    public DownloadRequest setTimeInterval(int timeInterval,TimeUnit timeUnit) {
        if(timeInterval>0){
            this.timeInterval=timeInterval;
        }
        this.timeUnit=timeUnit;

        return this;
    }
    /**
     * 设置文件名称
     * @param fileName
     * @return
     */
    public DownloadRequest setFileName(String fileName) {
        if (!TextUtils.isEmpty(fileName)) {
            this.fileName = fileName;
        }
        return this;
    }

    @Override
    protected <T> Observable<T> execute(Type type) {
        return (Observable<T>) apiService
                .downFile(suffixUrl, params)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .toFlowable(BackpressureStrategy.LATEST)
                .flatMap(new DownloadFunction(rootName,dirName,fileName))
                .sample(timeInterval, timeUnit)
                .observeOn(AndroidSchedulers.mainThread())
                .toObservable()
                .retryWhen(new ApiRetryFunc(retryCount, retryDelayMillis));
    }

    @Override
    protected <T> Observable<CacheResult<T>> cacheExecute(Type type) {
        return null;
    }

    @Override
    protected <T> void execute(BaseCallback<T> callback) {
        DisposableObserver disposableObserver = new DownCallbackSubscriber(callback);
        if (super.tag != null) {
            ApiManager.get().add(super.tag, disposableObserver);
        }

        if (super.mActivity != null) {
            if(!ApiManager.get().isContainTag(super.mActivity.getClass().getName())){
                super.mActivity.getLifecycle().addObserver(new BaseLifeCycleObserver(super.mActivity.getLifecycle(),super.mActivity));
            }
            ApiManager.get().add(super.mActivity.getClass().getName()+"_"+disposableObserver.hashCode(), disposableObserver);
        }

        if (super.mFragment != null) {
            if(!ApiManager.get().isContainTag(super.mFragment.getClass().getName())){
                super.mFragment.getLifecycle().addObserver(new BaseLifeCycleObserver(super.mFragment.getLifecycle(),mFragment));
            }
            ApiManager.get().add(super.mFragment.getClass().getName()+"_"+disposableObserver.hashCode(), disposableObserver);
        }

        this.execute(getType(callback)).subscribe(disposableObserver);
    }



    private String getDiskCachePath(Context context) {
        String cachePath;
        if ((Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable())
                && context.getExternalCacheDir() != null) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return cachePath;
    }
}
