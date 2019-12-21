package com.qpg.superhttp.core;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import io.reactivex.disposables.Disposable;

/**
 * @Description: 请求管理，方便中途取消请求
 */
public class ApiManager {
    private static ApiManager sInstance;

    private ConcurrentHashMap<Object, Disposable> arrayMaps;

    public static ApiManager get() {
        if (sInstance == null) {
            synchronized (ApiManager.class) {
                if (sInstance == null) {
                    sInstance = new ApiManager();
                }
            }
        }
        return sInstance;
    }

    private ApiManager() {
        arrayMaps = new ConcurrentHashMap<>();
    }
    public ConcurrentHashMap getTagMap(){
        return arrayMaps;
    }
    public void add(Object tag, Disposable disposable) {
        arrayMaps.put(tag, disposable);
    }

    public void remove(Object tag) {
        if (!arrayMaps.isEmpty()) {
            arrayMaps.remove(tag);
        }
    }

    public void removeAll() {
        if (!arrayMaps.isEmpty()) {
            arrayMaps.clear();
        }
    }

    public void cancel(Object tag) {
        if (arrayMaps.isEmpty()) {
            return;
        }
        if (arrayMaps.get(tag) == null) {
            return;
        }
        if (!arrayMaps.get(tag).isDisposed()) {
            arrayMaps.get(tag).dispose();

            arrayMaps.remove(tag);
        }
    }

    public void cancelSome(String subTag) {
        if (arrayMaps.isEmpty()) {
            return;
        }
        Set<Object> keys = arrayMaps.keySet();
        for (Object apiKey : keys) {
            if(apiKey instanceof String && ((String) apiKey).contains(subTag)){
                cancel(apiKey);
            }
        }
    }
    public boolean isContainTag(String subTag) {

        Set<Object> keys = arrayMaps.keySet();
        for (Object apiKey : keys) {
          //  System.out.println("122-------->"+apiKey);
            if(apiKey instanceof String && ((String) apiKey).contains(subTag)){
              //  System.out.println("22-------->"+apiKey);
                return true;
            }
        }
        return false;
    }
    public void cancelAll() {
        if (arrayMaps.isEmpty()) {
            return;
        }
        Set<Object> keys = arrayMaps.keySet();
        for (Object apiKey : keys) {
            cancel(apiKey);
        }
    }
}
