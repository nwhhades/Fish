package cn.haizhe.cat.boot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import cn.haizhe.cat.utils.LOG;

public class BootReceiver extends BroadcastReceiver {

    private static final String TAG = "BootReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            if (Intent.ACTION_BOOT_COMPLETED.equals(action)) {
                LOG.D(TAG, "onReceive: 接收到了开机广播");
            }
        }
    }

}
