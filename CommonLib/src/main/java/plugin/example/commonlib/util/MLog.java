package plugin.example.commonlib.util;

import android.util.Log;

import plugin.example.commonlib.Configs;


/**
 * 日志类
 */
public class MLog {

    /**
     * debug级别
     */
    public static int DEBUG_LEVEL = MLog.VERBOSE;

    /**
     * 最低级别
     */
    public static final int VERBOSE = 5;
    /**
     * Debug级别
     */
    public static final int DEBUG = 4;
    /**
     * 正常信息级别
     */
    public static final int INFO = 3;
    /**
     * 警告级别
     */
    public static final int WARN = 2;
    /**
     * 错误级别
     */
    public static final int ERROR = 1;


    /**
     * 设置Debug级别
     *
     * @param level
     */
    public static void setDebugLevel(int level) {
        DEBUG_LEVEL = level;
    }

    public static int v(String TAG, String msg) {
        if (DEBUG_LEVEL > VERBOSE && msg != null) {
            return Log.v(TAG, msg);
        } else {
            return 0;
        }
    }

    public static int d(String TAG, String msg) {
        if (DEBUG_LEVEL > DEBUG && msg != null) {
            return Log.d(TAG, msg);
        } else {
            return 0;
        }
    }

    public static int i(String TAG, String msg) {
        if (DEBUG_LEVEL > INFO && msg != null) {
            return Log.i(TAG, msg);
        } else {
            return 0;
        }
    }

    public static int w(String TAG, String msg) {
        if (DEBUG_LEVEL > WARN && msg != null) {
            return Log.w(TAG, msg);
        } else {
            return 0;
        }
    }

    public static int e(String TAG, String msg) {
        if (DEBUG_LEVEL > ERROR && msg != null) {
            return Log.e(TAG, msg);
        } else {
            return 0;
        }
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (Configs.DEBUG_LEVEL > ERROR && msg != null) {
            Log.e(tag, msg, tr);
        }
    }
}