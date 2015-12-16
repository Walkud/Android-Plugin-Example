package com.plugin.core;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.text.TextUtils;
import android.util.Base64;

import com.plugin.content.PluginDescriptor;
import com.plugin.core.stub.ui.PluginStubActivity;
import com.plugin.util.LogUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 插件组件动态绑定到宿主的虚拟stub组件
 */
public class PluginStubBinding {

    public static final String STUB_ACTIVITY_PRE = PluginStubActivity.class.getPackage().getName();

    private static final String ACTION_LAUNCH_MODE = "com.plugin.core.LAUNCH_MODE";

    private static final String ACTION_PROXY_SERVICE = "com.plugin.core.PROXY_SERVICE";

    private static final String PRE_SERVICE_MAPPING_NAME = "plugins.serviceMapping";

    private static final String SERVICE_MAPPING_KEY = "plugins.serviceMapping.map";
    private static final String SERVICE_PROCESS_MAPPING_KEY = "plugins.serviceProcessMapping.map";

    /**
     * key:stub Activity Name
     * value:plugin Activity Name
     */
    private static HashMap<String, String> singleTaskMapping = new HashMap<String, String>();
    private static HashMap<String, String> singleTopMapping = new HashMap<String, String>();
    private static HashMap<String, String> singleInstanceMapping = new HashMap<String, String>();
    /**
     * key:stub Service Name
     * value:plugin Service Name
     */
    private static HashMap<String, String> serviceMapping = new HashMap<String, String>();
    /**
     * 独立进程Service
     * key:stub Service Name
     * value:plugin Service Name
     */
    private static HashMap<String, String> serviceProcessMapping = new HashMap<String, String>();

    private static boolean isPoolInited = false;

    private static void initPool() {
        if (isPoolInited) {
            return;
        }

        loadStubActivity();

        loadStubService();

        isPoolInited = true;
    }

    /**
     * 初始化Activity映射
     */
    private static void loadStubActivity() {
        Intent launchModeIntent = new Intent();
        launchModeIntent.setAction(ACTION_LAUNCH_MODE);
        launchModeIntent.setPackage(PluginLoader.getApplicatoin().getPackageName());

        List<ResolveInfo> list = PluginLoader.getApplicatoin().getPackageManager().queryIntentActivities(launchModeIntent, PackageManager.MATCH_DEFAULT_ONLY);

        if (list != null && list.size() > 0) {
            for (ResolveInfo resolveInfo :
                    list) {
                if (resolveInfo.activityInfo.name.startsWith(STUB_ACTIVITY_PRE)) {

                    if (resolveInfo.activityInfo.launchMode == ActivityInfo.LAUNCH_SINGLE_TASK) {

                        singleTaskMapping.put(resolveInfo.activityInfo.name, null);

                    } else if (resolveInfo.activityInfo.launchMode == ActivityInfo.LAUNCH_SINGLE_TOP) {

                        singleTopMapping.put(resolveInfo.activityInfo.name, null);

                    } else if (resolveInfo.activityInfo.launchMode == ActivityInfo.LAUNCH_SINGLE_INSTANCE) {

                        singleInstanceMapping.put(resolveInfo.activityInfo.name, null);

                    }

                }
            }
        }
    }

    /**
     * 初始化Service映射
     */
    private static void loadStubService() {
        Intent launchModeIntent = new Intent();
        launchModeIntent.setAction(ACTION_PROXY_SERVICE);
        launchModeIntent.setPackage(PluginLoader.getApplicatoin().getPackageName());

        List<ResolveInfo> list = PluginLoader.getApplicatoin().getPackageManager().queryIntentServices(launchModeIntent, PackageManager.MATCH_DEFAULT_ONLY);

        if (list != null && list.size() > 0) {
            for (ResolveInfo resolveInfo :
                    list) {
                String name = resolveInfo.serviceInfo.name;
                String processName = resolveInfo.serviceInfo.processName;
                LogUtil.d("processName:" + processName);
                //判断是否为独立
                if (!TextUtils.isEmpty(processName) && !PluginLoader.getApplicatoin().getPackageName().equals(processName)) {
                    serviceProcessMapping.put(name, null);
                } else {
                    serviceMapping.put(name, null);
                }
            }

            //从磁盘恢复映射
            HashMap<String, String> mapping = restore(SERVICE_MAPPING_KEY);
            if (mapping != null) {
                serviceMapping.putAll(mapping);
            }

            //从磁盘恢复独立进程映射
            mapping = restore(SERVICE_PROCESS_MAPPING_KEY);
            if (mapping != null) {
                serviceProcessMapping.putAll(mapping);
            }

            save(serviceMapping, SERVICE_MAPPING_KEY);
            save(serviceProcessMapping, SERVICE_PROCESS_MAPPING_KEY);
        }
    }

    /**
     * 绑定Activity映射关系
     *
     * @param pluginActivityClassName
     * @param launchMode
     * @return
     */
    public static String bindLaunchModeStubActivity(String pluginActivityClassName, int launchMode) {

        initPool();

        HashMap<String, String> bindingMapping = null;

        if (launchMode == ActivityInfo.LAUNCH_SINGLE_TASK) {

            bindingMapping = singleTaskMapping;

        } else if (launchMode == ActivityInfo.LAUNCH_SINGLE_TOP) {

            bindingMapping = singleTopMapping;

        } else if (launchMode == ActivityInfo.LAUNCH_SINGLE_INSTANCE) {

            bindingMapping = singleInstanceMapping;

        }

        if (bindingMapping != null) {

            Iterator<Map.Entry<String, String>> itr = bindingMapping.entrySet().iterator();
            String idleStubActivityName = null;

            while (itr.hasNext()) {
                Map.Entry<String, String> entry = itr.next();
                if (entry.getValue() == null) {
                    if (idleStubActivityName == null) {
                        idleStubActivityName = entry.getKey();
                        //这里找到空闲的stubactivity以后，还需继续遍历，用来检查是否pluginActivityClassName已经绑定过了
                    }
                } else if (pluginActivityClassName.equals(entry.getValue())) {
                    //已绑定过，直接返回
                    return entry.getKey();
                }
            }

            //没有绑定到StubActivity，而且还有空余的stubActivity，进行绑定
            if (idleStubActivityName != null) {
                bindingMapping.put(idleStubActivityName, pluginActivityClassName);
                return idleStubActivityName;
            }

        }

        //绑定失败
        return PluginStubActivity.class.getName();
    }

    /**
     * 解绑Activity映射关系
     *
     * @param activityName
     * @param intent
     */
    public static void unBindLaunchModeStubActivity(String activityName, Intent intent) {
        if (activityName.startsWith(PluginStubBinding.STUB_ACTIVITY_PRE)) {
            if (intent != null) {
                ComponentName cn = intent.getComponent();
                if (cn != null) {
                    String pluginActivityName = cn.getClassName();
                    if (pluginActivityName.equals(singleTaskMapping.get(activityName))) {
                        singleTaskMapping.put(activityName, null);
                    } else if (pluginActivityName.equals(singleInstanceMapping.get(activityName))) {
                        singleInstanceMapping.put(activityName, null);
                    } else {
                        //对于standard和singleTop的launchmode，不做处理。
                    }
                }
            }
        }
    }

    /**
     * 通过映射反向查找目标ServiceClassName
     *
     * @param stubServiceName
     * @return
     */
    public static String getBindedPluginServiceName(String stubServiceName) {

        //在同进程映射中查找
        String targetServiceName = searchTargetServiceName(serviceMapping, SERVICE_MAPPING_KEY, stubServiceName);

        if (targetServiceName == null) {
            //从独立进程映射中中查找
            targetServiceName = searchTargetServiceName(serviceProcessMapping, SERVICE_PROCESS_MAPPING_KEY, stubServiceName);
        }

        return targetServiceName;
    }

    /**
     * 从磁盘找映射目标ServiceClassName
     *
     * @param map
     * @param key
     * @param stubServiceName
     * @return
     */
    private static String searchTargetServiceName(HashMap<String, String> map, String key, String stubServiceName) {
        Iterator<Map.Entry<String, String>> itr = map.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry<String, String> entry = itr.next();

            if (entry.getKey().equals(stubServiceName)) {
                LogUtil.d("searchTargetServiceName" + key, entry.getKey(), entry.getValue());
                return entry.getValue();
            }
        }

        //没有找到，尝试重磁盘恢复
        HashMap<String, String> mapping = restore(key);
        if (mapping != null) {
            itr = mapping.entrySet().iterator();
            while (itr.hasNext()) {
                Map.Entry<String, String> entry = itr.next();

                if (entry.getKey().equals(stubServiceName)) {
                    LogUtil.d("searchTargetServiceName" + key, entry.getKey(), entry.getValue());
                    map.put(stubServiceName, entry.getValue());
                    save(map, key);
                    return entry.getValue();
                }
            }
        }
        return null;
    }

    /**
     * 绑定Service映射关系
     *
     * @param pluginServiceClassName
     * @return
     */
    public static String bindStubService(String pluginServiceClassName) {

        initPool();
        PluginDescriptor pluginDescriptor = PluginLoader.getPluginDescriptorByClassName(pluginServiceClassName);
        boolean bool = pluginDescriptor.getProcessNames().containsKey(pluginServiceClassName);
        Iterator<Map.Entry<String, String>> itr;
        //判断是否为独立进程Service
        LogUtil.d("process bool:" + bool);
        if (bool) {
            itr = serviceProcessMapping.entrySet().iterator();
        } else {
            itr = serviceMapping.entrySet().iterator();
        }

        String idleStubServiceName = null;

        while (itr.hasNext()) {
            Map.Entry<String, String> entry = itr.next();
            if (entry.getValue() == null) {
                if (idleStubServiceName == null) {
                    idleStubServiceName = entry.getKey();
                    //这里找到空闲的idleStubServiceName以后，还需继续遍历，用来检查是否pluginActivityClassName已经绑定过了
                }
            } else if (pluginServiceClassName.equals(entry.getValue())) {
                //已经绑定过，直接返回
                LogUtil.d("已经绑定过", entry.getKey(), pluginServiceClassName);
                return entry.getKey();
            }
        }

        //没有绑定到StubService，而且还有空余的StubService，进行绑定
        if (idleStubServiceName != null) {
            LogUtil.d("添加绑定", idleStubServiceName, pluginServiceClassName);
            if (bool) { //判断是否为独立进程Service
                serviceProcessMapping.put(idleStubServiceName, pluginServiceClassName);
                //对serviceMapping持久化是因为如果service处于运行状态时app发生了crash，系统会自动恢复之前的service，此时插件映射信息查不到的话会再次crash
                save(serviceProcessMapping, SERVICE_PROCESS_MAPPING_KEY);
            } else {
                serviceMapping.put(idleStubServiceName, pluginServiceClassName);
                //对serviceMapping持久化是因为如果service处于运行状态时app发生了crash，系统会自动恢复之前的service，此时插件映射信息查不到的话会再次crash
                save(serviceMapping, SERVICE_MAPPING_KEY);
            }
            return idleStubServiceName;
        }

        //绑定失败
        LogUtil.d("绑定失败 process bool:" + bool, serviceMapping.size(), serviceProcessMapping.size());
        return null;
    }

    /**
     * 解绑Service映射关系
     *
     * @param pluginServiceClassName
     */
    public static String unBindStubService(String pluginServiceClassName) {
        //判断是否为跨进程Service
        PluginDescriptor pluginDescriptor = PluginLoader.getPluginDescriptorByClassName(pluginServiceClassName);
        boolean bool = pluginDescriptor.getProcessNames().containsKey(pluginServiceClassName);

        LogUtil.d("process bool:" + bool);

        HashMap<String, String> mapping = bool ? serviceProcessMapping : serviceMapping;
        String mappingKey = bool ? SERVICE_PROCESS_MAPPING_KEY : SERVICE_MAPPING_KEY;

        String stubServiceName = unBindStubServiceByKey(mapping, pluginServiceClassName, mappingKey);

        if (stubServiceName == null) {
            //没有找到，尝试重磁盘恢复
            mapping = restore(mappingKey);
            if (mapping != null) {
                stubServiceName = unBindStubServiceByKey(mapping, pluginServiceClassName, mappingKey);
            }
        }

        return stubServiceName;
    }

    /**
     * 解绑Service映射关系
     *
     * @param pluginServiceClassName
     */
    private static String unBindStubServiceByKey(HashMap<String, String> mapping, String pluginServiceClassName, String key) {
        Iterator<Map.Entry<String, String>> itr = mapping.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry<String, String> entry = itr.next();
            if (pluginServiceClassName.equals(entry.getValue())) {
                LogUtil.d("回收绑定", entry.getKey(), entry.getValue());
                //如果存在绑定关系，解绑
                mapping.put(entry.getKey(), null);
                save(mapping, key);
                return entry.getKey();
            }
        }

        return null;
    }

    /**
     * 缓存至磁盘中
     *
     * @param mapping
     * @param key
     * @return
     */
    private static boolean save(HashMap<String, String> mapping, String key) {

        printMap(mapping);
        ObjectOutputStream objectOutputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(mapping);
            objectOutputStream.flush();

            byte[] data = byteArrayOutputStream.toByteArray();
            String list = Base64.encodeToString(data, Base64.DEFAULT);

            PluginLoader.getApplicatoin()
                    .getSharedPreferences(PRE_SERVICE_MAPPING_NAME, Context.MODE_PRIVATE)
                    .edit().putString(key, list).commit();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (byteArrayOutputStream != null) {
                try {
                    byteArrayOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * 从磁盘中回复映射
     *
     * @param key
     * @return
     */
    private static HashMap<String, String> restore(String key) {
        String list = PluginLoader.getApplicatoin()
                .getSharedPreferences(PRE_SERVICE_MAPPING_NAME, Context.MODE_PRIVATE)
                .getString(key, "");
        Serializable object = null;
        if (!TextUtils.isEmpty(list)) {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                    Base64.decode(list, Base64.DEFAULT));
            ObjectInputStream objectInputStream = null;
            try {
                objectInputStream = new ObjectInputStream(byteArrayInputStream);
                object = (Serializable) objectInputStream.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (objectInputStream != null) {
                    try {
                        objectInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (byteArrayInputStream != null) {
                    try {
                        byteArrayInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        if (object != null) {

            HashMap<String, String> mapping = (HashMap<String, String>) object;
            return mapping;
        }
        return null;
    }

    private static void printMap(HashMap<String, String> map) {
        Iterator<Map.Entry<String, String>> itr = map.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry<String, String> entry = itr.next();
            LogUtil.d("printMap", entry.getKey(), entry.getValue());
        }

    }
}
