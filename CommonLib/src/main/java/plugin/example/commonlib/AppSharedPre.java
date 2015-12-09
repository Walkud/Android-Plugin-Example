package plugin.example.commonlib;

import android.content.Context;

import plugin.example.commonlib.util.SecurePreferences;


/**
 * 应用程序配置类：用于保存用户相关信息及设置
 */
public class AppSharedPre extends SecurePreferences {

    private static AppSharedPre mAppSharedPre;

    private AppSharedPre(Context context) {
        super(context);
    }

    public static AppSharedPre getInstance(Context context) {
        if (mAppSharedPre == null) {
            mAppSharedPre = new AppSharedPre(context);
        }
        return mAppSharedPre;
    }
}
