package com.qpg.superhttp.transformer;

import com.qpg.superhttp.mode.DownProgress;
import org.reactivestreams.Publisher;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;

/**
 * @Description: ResponseBody转T
 */
public class DownloadFunction implements Function<ResponseBody, Publisher<DownProgress>> {

    private String rootName,dirName,fileName;
    public DownloadFunction(String rootName,String dirName,String fileName){
        this.rootName=rootName;
        this.dirName=dirName;
        this.fileName=fileName;
    }

    @Override
    public Publisher<DownProgress> apply(final ResponseBody responseBody) throws Exception {
        return Flowable.create(new FlowableOnSubscribe<DownProgress>() {
            @Override
            public void subscribe(FlowableEmitter<DownProgress> subscriber) throws Exception {
                File dir = getDiskCacheDir(rootName, dirName);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File file = new File(dir.getPath() + File.separator + fileName);
                saveFile(subscriber, file, responseBody);
            }
        }, BackpressureStrategy.LATEST);
    }

    private void saveFile(FlowableEmitter<DownProgress> sub, File saveFile, ResponseBody resp) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            try {
                int readLen;
                int downloadSize = 0;
                byte[] buffer = new byte[8192];

                DownProgress downProgress = new DownProgress();
                inputStream = resp.byteStream();
                outputStream = new FileOutputStream(saveFile);

                long contentLength = resp.contentLength();
                downProgress.setTotalSize(contentLength);

                while ((readLen = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, readLen);
                    downloadSize += readLen;
                    downProgress.setDownloadSize(downloadSize);
                    sub.onNext(downProgress);
                }
                outputStream.flush();
                sub.onComplete();
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
                if (resp != null) {
                    resp.close();
                }
            }
        } catch (IOException e) {
            if(!sub.isCancelled()){
                sub.onError(e);
            }
        }
    }

    private File getDiskCacheDir(String rootName, String dirName) {
        return new File(rootName + File.separator + dirName);
    }
}
