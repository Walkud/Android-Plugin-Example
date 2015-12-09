package com.plugin.util;

import android.content.Context;

/**
 * Created by cailiming
 */
public class ResourceUtil {

    public static String getString(String value, Context pluginContext) {
        String idHex = null;
        if (value != null && value.startsWith("@") && value.length() == 9) {
            idHex = value.replace("@", "");

        } else if (value != null && value.startsWith("@android:") && value.length() == 17) {
            idHex = value.replace("@android:", "");
        }

        if (idHex != null) {
            try {
                int id = Integer.parseInt(idHex, 16);
                //此时context可能还没有初始化
                if (pluginContext != null) {
                    String des = pluginContext.getString(id);
                    return des;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return value;
    }

    public static Boolean getBoolean(String value, Context pluginContext) {
        String idHex = null;
        if (value != null && value.startsWith("@") && value.length() == 9) {
            idHex = value.replace("@", "");

        } else if (value != null && value.startsWith("@android:") && value.length() == 17) {
            idHex = value.replace("@android:", "");
        }

        if (idHex != null) {
            try {
                int id = Integer.parseInt(idHex, 16);
                //此时context可能还没有初始化
                if (pluginContext != null) {
                    return pluginContext.getResources().getBoolean(id);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (value != null) {
            return Boolean.parseBoolean(value);
        }

        return null;
    }

    public static int getResourceId(String value) {
        String idHex = null;
        if (value != null && value.startsWith("@") && value.length() == 9) {
            idHex = value.replace("@", "");

        } else if (value != null && value.startsWith("@android:") && value.length() == 17) {
            idHex = value.replace("@android:", "");
        }
        if (idHex != null) {
            try {
                int id = Integer.parseInt(idHex, 16);
                return id;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }
}
