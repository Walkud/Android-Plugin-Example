package plugin.example.commonlib.service.manager;

/**
 * Created by Walkud on 15/11/25.
 * 插件管理回调接口
 */
public interface IPluginManager {
    /**
     * 开始
     */
    void onStart();

    /**
     * 完成
     *
     * @param isSuccess
     */
    void onFinish(boolean isSuccess);

    /**
     * 进度百分比
     *
     * @param progress 百分比  max ：100
     */
    void onProgressChange(int progress);
}
