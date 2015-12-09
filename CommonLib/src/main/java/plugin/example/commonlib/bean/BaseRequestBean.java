package plugin.example.commonlib.bean;

import android.content.Context;

import java.util.HashMap;

/**
 * 请求参数封装 抽象类
 *
 * @author wyg
 */
public abstract class BaseRequestBean extends HashMap<String, String> {

    /**
     * 添加必传参数
     *
     * @param context
     */
    public void putMustParams(Context context) {
        putParams();
    }

    /**
     * 获取接口URL
     */
    public abstract String getApi();

    /**
     * 设置参数
     */
    public abstract void putParams();

    public String put(String key, int value) {
        return super.put(key, String.valueOf(value));
    }
}
