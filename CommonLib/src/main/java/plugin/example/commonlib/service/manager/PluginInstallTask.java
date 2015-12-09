package plugin.example.commonlib.service.manager;

/**
 * Created by Walkud on 15/11/25.
 */

import android.content.Context;
import android.os.AsyncTask;

import com.plugin.core.PluginLoader;

import java.io.File;
import java.io.IOException;

import plugin.example.commonlib.bean.PluginData;
import plugin.example.commonlib.network.toolbox.FileDownloader;
import plugin.example.commonlib.util.MLog;
import plugin.example.commonlib.util.StringUtils;

/**
 * 插件异步安装任务
 */
public class PluginInstallTask extends AsyncTask<Void, Integer, Boolean> {
    private static final String TAG = PluginInstallTask.class.getSimpleName();

    private Context mContext;
    private PluginData mPluginData;
    private IPluginManager mListener;

    public PluginInstallTask(Context context, PluginData pd, IPluginManager listener) {
        this.mContext = context;
        this.mPluginData = pd;
        this.mListener = listener;

        buildFilePath();
    }

    /**
     * 构建插件下载路径
     */
    private void buildFilePath() {
        String installPath = mContext.getCacheDir().getAbsolutePath() + File.separator;
        String url = mPluginData.getUrl();

        if (!StringUtils.isEmpty(url)) {
            MLog.d(TAG, "url :" + url);
            //截取apk名称
            String name = url.substring(url.lastIndexOf(File.separator) + 1);
            installPath += name;
        }
        MLog.d(TAG, "installPath :" + installPath);
        mPluginData.setPath(installPath);
    }

    @Override
    protected void onPreExecute() {
        mPluginData.setInstalling(true);
        if (mListener != null) {
            //开始安装
            mListener.onStart();
        }
    }

    @Override
    protected Boolean doInBackground(Void... params) {

        //创建文件下载器
        FileDownloader fileDownloader = new FileDownloader(new FileDownloader.ProgressListener() {
            @Override
            public void onProgressChange(long fileSize, long downloadedSize) {
                //下载进度回调
//                MLog.d(TAG, "fileSize:" + fileSize + ",downloadedSize:" + downloadedSize);
                //下载进度占总进度的80%,安装插件占20%
                int progress = (int) (downloadedSize / (float) fileSize * 80);
                publishProgress(progress);
            }
        });

        try {
            //下载文件
            boolean success = fileDownloader.downFile(mPluginData.getUrl(), mPluginData.getPath());

            if (success) {
                //安装插件
                int val = PluginLoader.installPlugin(mPluginData.getPath());
                //判断是否安装成功
                success = val == 0;
            }
            publishProgress(100);
            return success;
        } catch (IOException e) {
            e.printStackTrace();
            MLog.e(TAG, e.getMessage(), e);
        }

        return false;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        if (mListener != null) {
            //更新进度
            mListener.onProgressChange(values[0]);
        }
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        mPluginData.setInstalling(false);
        if (mListener != null) {
            //安装完成
            mListener.onFinish(aBoolean);
        }
    }


}
