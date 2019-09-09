package com.qpg.superhttp.core;

import android.content.Context;

import com.qpg.superhttp.cache.DiskCache;
import com.qpg.superhttp.config.SuperConfig;
import com.qpg.superhttp.mode.ApiHost;
import com.qpg.superhttp.mode.CacheMode;
import com.qpg.superhttp.mode.CacheResult;
import com.qpg.superhttp.strategy.ICacheStrategy;

import java.io.File;
import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @Description: 针对响应数据进行缓存管理
 */
public class ApiCache {
    private final DiskCache diskCache;
    private String cacheKey;

    private static abstract class SimpleSubscribe<T> implements ObservableOnSubscribe<T> {
        @Override
        public void subscribe(ObservableEmitter<T> subscriber) throws Exception {
            try {
                T data = execute();
                if (!subscriber.isDisposed() && data != null) {
                    subscriber.onNext(data);
                }
            } catch (Throwable e) {

                Exceptions.throwIfFatal(e);
                if (!subscriber.isDisposed()) {
                    subscriber.onError(e);
                }
                return;
            }
            if (!subscriber.isDisposed()) {
                subscriber.onComplete();
            }
        }

        abstract T execute() throws Throwable;
    }

    private ApiCache(Context context, String cacheKey, long time) {
        this.cacheKey = cacheKey;
        this.diskCache = new DiskCache(context).setCacheTime(time);
    }

    private ApiCache(Context context, File diskDir, long diskMaxSize, String cacheKey, long time) {
        this.cacheKey = cacheKey;
        diskCache = new DiskCache(context, diskDir, diskMaxSize).setCacheTime(time);
    }

    public <T> ObservableTransformer<T, CacheResult<T>> transformer(CacheMode cacheMode, final Type type) {
        final ICacheStrategy strategy = loadStrategy(cacheMode);//获取缓存策略
        return new ObservableTransformer<T, CacheResult<T>>() {
            @Override
            public ObservableSource<CacheResult<T>> apply(Observable<T> apiResultObservable) {

                return strategy.execute(ApiCache.this, ApiCache.this.cacheKey, apiResultObservable, type);
            }
        };
    }


    public Observable<String> get(final String key) {
        return Observable.create(new SimpleSubscribe<String>() {
            @Override
            String execute() {
                return diskCache.get(key);
            }
        });
    }

    public <T> Observable<Boolean> put(final String key, final T value) {
        return Observable.create(new SimpleSubscribe<Boolean>() {
            @Override
            Boolean execute() throws Throwable {
                diskCache.put(key, value);
                return true;
            }
        });
    }

    public boolean containsKey(final String key) {
        return diskCache.contains(key);
    }

    public void remove(final String key) {
        diskCache.remove(key);
    }

    public boolean isClosed() {
        return diskCache.isClosed();
    }

    public Disposable clear() {
        return Observable.create(new SimpleSubscribe<Boolean>() {
            @Override
            Boolean execute() throws Throwable {
                diskCache.clear();
                return true;
            }
        }).subscribeOn(Schedulers.io()).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean status) throws Exception {

            }
        });
    }

    public ICacheStrategy loadStrategy(CacheMode cacheMode) {
        try {
            String pkName = ICacheStrategy.class.getPackage().getName();
            return (ICacheStrategy) Class.forName(pkName + "." + cacheMode.getClassName()).newInstance();
        } catch (Exception e) {
            throw new RuntimeException("loadStrategy(" + cacheMode + ") err!!" + e.getMessage());
        }
    }

    public static final class Builder {
        private final Context context;
        private File diskDir;
        private long diskMaxSize;
        private long cacheTime = SuperConfig.CACHE_NEVER_EXPIRE;
        private String cacheKey = ApiHost.getHost();

        public Builder(Context context) {
            this.context = context;
        }

        public Builder(Context context, File diskDir, long diskMaxSize) {
            this.context = context;
            this.diskDir = diskDir;
            this.diskMaxSize = diskMaxSize;
        }

        public Builder cacheKey(String cacheKey) {
            this.cacheKey = cacheKey;
            return this;
        }

        public Builder cacheTime(long cacheTime) {
            this.cacheTime = cacheTime;
            return this;
        }

        public ApiCache build() {
            if (diskDir == null || diskMaxSize == 0) {
                return new ApiCache(context, cacheKey, cacheTime);
            } else {
                return new ApiCache(context, diskDir, diskMaxSize, cacheKey, cacheTime);
            }
        }

    }
}
