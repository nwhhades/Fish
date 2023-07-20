package cn.haizhe.cat;

import android.app.Application;

import com.hjq.toast.Toaster;
import com.tencent.mmkv.MMKV;
import com.tencent.mmkv.MMKVLogLevel;

import cn.haizhe.cat.utils.LOG;
import cn.haizhe.cat.widget.toaster.BigBlackToastStyle;

public abstract class BaseApp extends Application {

    private static final String TAG = "BaseApp";

    protected abstract boolean openLog();

    protected abstract void init();

    @Override
    public void onCreate() {
        super.onCreate();
        LOG.setOpen(openLog());
        LOG.D(TAG, "onCreate: " + this);
        initMMKV();
        initToaster();
        init();
    }

    private void initMMKV() {
        String root = MMKV.initialize(this);
        LOG.D(TAG, "initMMKV: MMKV root is " + root);
        MMKV.setLogLevel(LOG.isOpen() ? MMKVLogLevel.LevelDebug : MMKVLogLevel.LevelNone);
    }

    private void initToaster() {
        Toaster.init(this, new BigBlackToastStyle());
    }

}
