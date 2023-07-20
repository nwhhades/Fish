package cn.haizhe.cat.cache;

import com.tencent.mmkv.MMKV;

import java.util.HashMap;
import java.util.Map;

public enum CacheUtils {
    instance;

    private final Map<String, Cache> cacheMap;
    private final Cache defaultCache;

    CacheUtils() {
        cacheMap = new HashMap<>();
        defaultCache = new Cache(MMKV.defaultMMKV());
    }

    public synchronized Cache getCache(String key) {
        if (key == null || "".equals(key)) {
            return defaultCache;
        }
        Cache cache = cacheMap.get(key);
        if (cache == null) {
            //获取对应的缓存对象
            cache = new Cache(MMKV.mmkvWithID(key));
            cacheMap.put(key, cache);
        }
        return cache;
    }

    public Cache getCache() {
        return getCache(null);
    }

}
