package plugin.example.commonlib;

import android.content.Context;

import com.plugin.core.PluginApplication;
import com.squareup.picasso.Picasso;

import plugin.example.commonlib.util.MyToast;


/**
 * 全局应用程序类
 */
public class AppContext extends PluginApplication {

    private static final String TAG = AppContext.class.getSimpleName();

    private static Context mAppContext;


    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = this;

        Picasso.with(this).setIndicatorsEnabled(true);

    }

    /**
     * 插件异常信息回调,上传友盟
     *
     * @param message
     */
    @Override
    public void onExceptionEvent(String message) {
        super.onExceptionEvent(message);
        MyToast.show(this, message);
    }

    public static Context getAppContext() {
        return mAppContext;
    }
}
