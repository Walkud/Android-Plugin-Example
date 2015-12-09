package com.plugin.util;

import android.util.Log;

import com.plugin.core.PluginApplication;

public class LogUtil {

    private static final boolean isDebug = true;

    private static final int stackLevel = 4;

    public static void v(Object... msg) {
        printLog(Log.VERBOSE, msg);
    }

    public static void d(Object... msg) {
        printLog(Log.DEBUG, msg);
    }

    public static void e(Object... msg) {
        printLog(Log.ERROR, msg);
    }

    private static void printLog(int level, Object... msg) {
        if (isDebug) {
            StringBuilder str = new StringBuilder();

            if (msg != null) {
                for (Object obj : msg) {
                    str.append("★").append(obj);
                }
                if (str.length() > 0) {
                    str.deleteCharAt(0);
                }
            } else {
                str.append("null");
            }
            try {
                StackTraceElement[] sts = Thread.currentThread().getStackTrace();
                StackTraceElement st = null;
                String tag = null;
                if (sts != null && sts.length > stackLevel) {
                    st = sts[stackLevel];
                    if (st != null) {
                        String fileName = st.getFileName();
                        tag = (fileName == null) ? "Unkown" : fileName.replace(".java", "");
                        str.insert(0, "【" + tag + "." + st.getMethodName() + "() line " + st.getLineNumber() + "】\n>>>[")
                                .append("]");
                    }
                }

                tag = (tag == null) ? "Plugin" : ("Plugin_" + tag);
                // use logcat log
                while (str.length() > 0) {
                    if (level == Log.DEBUG) {
                        Log.d(tag, str.substring(0, Math.min(2000, str.length())).toString());
                    } else if (level == Log.ERROR) {
                        String errMsg = str.substring(0, Math.min(2000, str.length())).toString();
                        Log.e(tag, errMsg);
                        throwExceptionEvent(errMsg);
                    } else {
                        Log.v(tag, str.substring(0, Math.min(2000, str.length())).toString());
                    }
                    str.delete(0, 2000);
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    public static void printStackTrace() {
        if (isDebug) {
            try {
                StackTraceElement[] sts = Thread.currentThread().getStackTrace();
                StringBuilder sb = new StringBuilder();
                for (StackTraceElement stackTraceElement : sts) {
                    sb.append(stackTraceElement.toString() + "\n");
                }

                Log.e("Log_trace", sb.toString());
                throwExceptionEvent(sb.toString());

            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    public static void printException(String msg, Throwable e) {
        if (isDebug) {
            Log.e("Log_trace", msg, e);
        }
    }

    private static void throwExceptionEvent(String msg) {
        PluginApplication app = PluginApplication.getInstance();
        if (app != null) {
            app.onExceptionEvent(msg);
        }
    }
}
