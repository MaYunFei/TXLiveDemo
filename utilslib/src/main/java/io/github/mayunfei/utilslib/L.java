package io.github.mayunfei.utilslib;

import android.util.Log;

/**
 * 日志打印控制
 * Created by mayunfei on 17-9-14.
 */

public class L {
    private static final String TAG = "日志 ";
    private static final boolean IS_DEBUG = true;

    private L() {
    }

    public static void i(String tag, String msg) {
        if (IS_DEBUG)
            Log.i(tag, msg);
    }

    public static void  i(String msg) {
        i(TAG, msg);
    }
}
