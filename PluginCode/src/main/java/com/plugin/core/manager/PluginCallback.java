package com.plugin.core.manager;

public interface PluginCallback {

    /**
     * 广播Action
     */
    String ACTION_PLUGIN_CHANGED = "com.plugin.core.action_plugin_changed";
    /**
     * EXTRA KEY  By type
     */
    String EXTRA_TYPE = "type";
    /**
     * EXTRA KEY  By id
     */
    String EXTRA_ID = "id";
    /**
     * EXTRA KEY  By version
     */
    String EXTRA_VERSION = "version";


    void onPluginLoaderInited();

    void onPluginInstalled(String packageName, String version);

    void onPluginRemoved(String packageName);

    void onPluginStarted(String packageName);

    void onPluginRemoveAll();
}
