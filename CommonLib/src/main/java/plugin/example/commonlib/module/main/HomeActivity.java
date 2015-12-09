package plugin.example.commonlib.module.main;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.plugin.core.PluginLoader;
import com.plugin.core.manager.PluginCallback;
import com.plugin.util.FileUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import plugin.example.commonlib.R;
import plugin.example.commonlib.bean.PluginData;
import plugin.example.commonlib.common.PluginHelper;
import plugin.example.commonlib.module.main.adapter.PluginGridAdapter;
import plugin.example.commonlib.service.PluginManagerService;
import plugin.example.commonlib.service.PluginManagerService.ManagerBinder;
import plugin.example.commonlib.service.manager.IPluginManager;
import plugin.example.commonlib.service.manager.PluginManagerAdapter;
import plugin.example.commonlib.util.AssetsUtil;
import plugin.example.commonlib.util.MyToast;

/**
 * Created by Wolkud on 15/9/8.
 */
public class HomeActivity extends BaseActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    //安装方式标记 true:为通过网络下载apk进行安装，false本地测试安装
    private static final boolean FLAG = false;

    GridView homePluginListGridview;

    private List<PluginData> mPluginDatas;
    private PluginGridAdapter mPluginGridAdapter;

    private ManagerBinder mManagerBinder;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SharedPreferences spre = this.getSharedPreferences("app_shared", Context.MODE_APPEND);
        SharedPreferences.Editor editor = spre.edit();
        editor.putString("Test_Shared", "AppDemo");
        editor.commit();

        // 监听插件安装 安装新插件后刷新当前页面
        registerReceiver(mPluginChangeBroadcast, new IntentFilter(PluginCallback.ACTION_PLUGIN_CHANGED));

        bindView();
        init();

        bindPluginManagerService();
    }

    /**
     * bind控件
     */
    private void bindView() {
        homePluginListGridview = (GridView) findViewById(R.id.home_plugin_list_gridview);
        homePluginListGridview.setOnItemClickListener(this);
        homePluginListGridview.setOnItemLongClickListener(this);
    }

    /**
     * 初始化
     */
    private void init() {

        mPluginDatas = getTestData();

        mPluginGridAdapter = new PluginGridAdapter(this, mPluginDatas);
        homePluginListGridview.setAdapter(mPluginGridAdapter);
    }

    /**
     * 生成测试数据
     *
     * @return
     */
    private List<PluginData> getTestData() {

        try {
            String testStr = AssetsUtil.getAssetsText(this, "PluginData.txt");
            Gson gson = new GsonBuilder()
                    .enableComplexMapKeySerialization() //支持Map的key为复杂对象的形式
                    .setPrettyPrinting() //对json结果格式化.
                    .create();
            return gson.fromJson(testStr, new TypeToken<List<PluginData>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    /**
     * 插件列表变动广播
     */
    private final BroadcastReceiver mPluginChangeBroadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mPluginGridAdapter.notifyDataSetChanged();
        }
    };


    /**
     * Debug 测试代码
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     * @return
     */
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        PluginData pluginData = mPluginDatas.get(position);
        installPlugin(pluginData);
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PluginData pluginData = mPluginDatas.get(position);
        String version = PluginLoader.getPluginVersionById(pluginData.getPluginId());
        if (version != null && version.startsWith(pluginData.getVersion())) {
            PluginHelper.startActivity(this, pluginData.getBootClass(), pluginData.getParams());
        } else {
            installPlugin(pluginData);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mPluginChangeBroadcast);
    }

    /**
     * 安装插件
     *
     * @param pluginData
     */
    private void installPlugin(PluginData pluginData) {

        if (FLAG) {
            //通过网络的形式下载并安装插件
            installPlugin(pluginData, new PluginManagerAdapter() {
                @Override
                public void onStart() {
                    mPluginGridAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFinish(boolean isSuccess) {
                    MyToast.show(mAppContext, "安装完成:" + isSuccess);
                    mPluginGridAdapter.notifyDataSetChanged();
                }
            });
        } else {
            //本地测试安装
            copyAndInstall(pluginData.getPath());
        }

    }

    /**
     * 安装插件
     *
     * @param path
     */
    private void copyAndInstall(String path) {
        int success = -1;
        try {
            InputStream assestInput = getAssets().open(path);
            String dest = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + path;
            if (FileUtil.copyFile(assestInput, dest)) {
                success = PluginLoader.installPlugin(dest);
            } else {
                assestInput = getAssets().open(path);
                dest = getCacheDir().getAbsolutePath() + "/" + path;
                if (FileUtil.copyFile(assestInput, dest)) {
                    success = PluginLoader.installPlugin(dest);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        switch (success) {
            case 0:
                MyToast.show(this, "安装成功 apk：" + path);
                break;
            default:
                MyToast.show(this, "安装失败   apk：" + path);
                break;
        }
    }


    /**
     * 绑定插件管理Service
     */
    private void bindPluginManagerService() {
        Intent intent = new Intent(this, PluginManagerService.class);
        bindService(intent, mPluginManagerConn, Context.BIND_AUTO_CREATE);
    }

    /**
     * 插件管理Conncetion
     */
    private ServiceConnection mPluginManagerConn = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            MyToast.show(mAppContext, "BindService 失败");
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mManagerBinder = (ManagerBinder) service;
            MyToast.show(mAppContext, "Service绑定成功:" + mManagerBinder);
        }
    };

    /**
     * 安装插件
     */
    public void installPlugin(PluginData pluginData, IPluginManager listener) {
        if (mManagerBinder != null) {
            mManagerBinder.install(pluginData, listener);
        }
    }

}
