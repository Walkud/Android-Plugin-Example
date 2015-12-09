package com.plugin.core.manager;

import android.content.Intent;

import com.plugin.core.PluginLoader;
import com.plugin.util.LogUtil;

/**
 * Created by Administrator on 2015/9/13.
 */
public class PluginCallbackImpl implements PluginCallback {

    @Override
    public void onPluginLoaderInited() {
        LogUtil.d("PluginLoader inited");
    }

    @Override
    public void onPluginInstalled(String packageName, String version) {
        Intent intent = new Intent(ACTION_PLUGIN_CHANGED);
        intent.putExtra(EXTRA_TYPE, "install");
        intent.putExtra(EXTRA_ID, packageName);
        intent.putExtra(EXTRA_VERSION, version);
        PluginLoader.getApplicatoin().sendBroadcast(intent);
    }

    @Override
    public void onPluginRemoved(String packageName) {
        Intent intent = new Intent(ACTION_PLUGIN_CHANGED);
        intent.putExtra(EXTRA_TYPE, "remove");
        intent.putExtra(EXTRA_ID, packageName);
        PluginLoader.getApplicatoin().sendBroadcast(intent);
    }

    @Override
    public void onPluginStarted(String packageName) {
        Intent intent = new Intent(ACTION_PLUGIN_CHANGED);
        intent.putExtra(EXTRA_TYPE, "init");
        intent.putExtra(EXTRA_ID, packageName);
        PluginLoader.getApplicatoin().sendBroadcast(intent);
    }

    @Override
    public void onPluginRemoveAll() {
        Intent intent = new Intent(ACTION_PLUGIN_CHANGED);
        intent.putExtra("type", "remove_all");
        PluginLoader.getApplicatoin().sendBroadcast(intent);
    }

}
