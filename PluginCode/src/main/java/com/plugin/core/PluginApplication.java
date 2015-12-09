package com.plugin.core;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;

public class PluginApplication extends Application {


    private static PluginApplication sPluginApplication;

    @Override
    protected void attachBaseContext(Context base) {

        super.attachBaseContext(base);
        sPluginApplication = this;

        PluginLoader.initLoader(this);
    }

    public static PluginApplication getInstance() {
        return sPluginApplication;
    }

    public void onExceptionEvent(String message) {
    }

    /**
     * 重写这个方法是为了支持Receiver,否则会出现ClassCast错误
     *
     * @return
     */
    @Override
    public Context getBaseContext() {
        return ((ContextWrapper) super.getBaseContext()).getBaseContext();
    }
}
