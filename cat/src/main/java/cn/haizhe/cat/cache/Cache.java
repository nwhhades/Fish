package cn.haizhe.cat.cache;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.LogUtils;
import com.tencent.mmkv.MMKV;

public class Cache {

    private static final String TAG = "MMKV缓存";
    private static final String KEY_TIME_SUFFIX = "_TIME";
    private final MMKV mmkv;

    public Cache(@NonNull MMKV mmkv) {
        this.mmkv = mmkv;
    }

    public String get(String key, String def) {
        String value = def;
        if (key != null) {
            long outTime = mmkv.getLong(key + KEY_TIME_SUFFIX, 0);
            if (outTime > System.currentTimeMillis() || outTime <= 0) {
                //没有过期 或者  过期时间小于等于0
                value = mmkv.getString(key, def);
            } else {
                //过期了，移除保存的值
                mmkv.removeValueForKey(key);
                mmkv.removeValueForKey(key + KEY_TIME_SUFFIX);
            }
        }
        LogUtils.dTag(TAG, "get方法: \nmmkv数据库:" + mmkv.mmapID() + " \nkey值:" + key + " \ndef值:" + def + " \nvalue值:" + value);
        return value;
    }

    public void put(String key, String value, long cacheTime) {
        LogUtils.dTag(TAG, "put方法: \nmmkv数据库:" + mmkv.mmapID() + " \nkey值:" + key + " \nvalue值:" + value + " \ncacheTime值:" + cacheTime);
        if (key != null && value != null) {
            mmkv.putString(key, value);
            mmkv.putLong(key + KEY_TIME_SUFFIX, cacheTime > 0 ? (System.currentTimeMillis() + cacheTime) : 0);
        }
    }

    public void put(String key, String value) {
        put(key, value, 0);
    }

}
