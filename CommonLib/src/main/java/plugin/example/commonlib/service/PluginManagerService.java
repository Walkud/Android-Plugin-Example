package plugin.example.commonlib.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import plugin.example.commonlib.bean.PluginData;
import plugin.example.commonlib.service.manager.IPluginManager;
import plugin.example.commonlib.service.manager.PluginInstallTask;

/**
 * 插件管理Service
 */
public class PluginManagerService extends Service {

    private ManagerBinder mManagerBinder = new ManagerBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return mManagerBinder;
    }


    /**
     * 插件安装Binder
     */
    public class ManagerBinder extends Binder {

        /**
         * 安装插件
         *
         * @param pluginData
         * @param listener
         */
        public void install(PluginData pluginData, IPluginManager listener) {
            new PluginInstallTask(getApplication(), pluginData, listener).execute();
        }

        /**
         * 删除插件
         *
         * @param pluginData
         */
        public void remove(PluginData pluginData) {
        }
    }

}
