package com.qpg.superhttp.strategy;

import com.qpg.superhttp.core.ApiCache;
import com.qpg.superhttp.mode.CacheResult;

import java.lang.reflect.Type;

import io.reactivex.Observable;

/**
 * @Description: 缓存策略--只取缓存
 */
public class OnlyCacheStrategy<T> extends CacheStrategy<T> {
    @Override
    public <T> Observable<CacheResult<T>> execute(ApiCache apiCache, String cacheKey, Observable<T> source, Type type) {
        return loadCache(apiCache, cacheKey, type);
    }
}
