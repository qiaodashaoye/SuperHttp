package com.qpg.superhttp.callback;

/**
 * @Description: 上传进度回调
 */
public interface UCallback {
    void onProgress(long currentLength, long totalLength, float percent);
    void onFail(int errCode, String errMsg);
}
