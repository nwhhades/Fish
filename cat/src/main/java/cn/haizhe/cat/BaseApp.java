package cn.haizhe.cat;

import android.app.Application;
import android.util.Log;

import com.blankj.utilcode.util.LogUtils;
import com.hjq.toast.Toaster;
import com.tencent.mmkv.MMKV;
import com.tencent.mmkv.MMKVLogLevel;

import cn.haizhe.cat.widget.toaster.BigBlackToastStyle;

public abstract class BaseApp extends Application {

    private static final String TAG = "BaseApp";

    protected abstract boolean openLog();

    protected abstract void init();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: 应用被创建" + this);
        //设置log开关
        LogUtils.getConfig().setLogSwitch(true);
        initMMKV();
        initToaster();
        init();
    }

    private void initMMKV() {
        String root = MMKV.initialize(this);
        MMKV.setLogLevel(LogUtils.getConfig().isLogSwitch() ? MMKVLogLevel.LevelDebug : MMKVLogLevel.LevelNone);
        LogUtils.dTag(TAG, "initMMKV: MMKV root is " + root);
    }

    private void initToaster() {
        Toaster.init(this, new BigBlackToastStyle());
    }

}
