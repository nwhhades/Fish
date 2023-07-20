package cn.haizhe.cat.utils;

import android.util.Log;

public class LOG {

    private static final String TAG = "CAT-log";
    private static final String splice_left = " ---> ";
    private static final String splice_right = " <--- ";
    private static boolean open = true;

    public static void setOpen(boolean open) {
        LOG.open = open;
    }

    public static boolean isOpen() {
        return open;
    }

    public static void D(String tag, String msg) {
        if (isOpen()) {
            Log.e(TAG, tag + splice_left);
            Log.d(TAG, splice_left + "\n" + msg);
            Log.e(TAG, splice_right + tag);
        }
    }

    public static void D(String tag, String msg, Throwable throwable) {
        if (isOpen()) {
            Log.e(TAG, tag + splice_left);
            Log.d(TAG, splice_left + "\n" + msg, throwable);
            Log.e(TAG, splice_right + tag);
        }
    }

}
