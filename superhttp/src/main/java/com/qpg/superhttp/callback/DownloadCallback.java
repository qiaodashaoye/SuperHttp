package com.qpg.superhttp.callback;

/**
 * @Description: 下载进度回调
 */
public abstract class DownloadCallback<T> extends BaseCallback<T>{
   public abstract void onProgress(long currentLength, long totalLength, float percent);
}
