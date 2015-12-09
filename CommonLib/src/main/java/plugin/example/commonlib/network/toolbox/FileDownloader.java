package plugin.example.commonlib.network.toolbox;

import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Walkud on 15/11/20.
 * 文件下载
 */
public class FileDownloader {

    private static final String TAG = FileDownloader.class.getSimpleName();

    private ProgressListener mListener;

    public FileDownloader(ProgressListener listener) {
        this.mListener = listener;
    }

    /**
     * 文件下载进度接口
     */
    public interface ProgressListener {

        void onProgressChange(long fileSize, long downloadedSize);

    }


    /**
     * 下载文件
     *
     * @param url
     * @param path
     * @return
     * @throws IOException
     */
    public boolean downFile(String url, String path) throws IOException {
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            URL myURL = new URL(url);
            URLConnection conn = myURL.openConnection();
            conn.connect();
            is = conn.getInputStream();
            long fileSize = conn.getContentLength();//根据响应获取文件大小
            if (fileSize <= 0) throw new RuntimeException("无法获知文件大小 ");
            if (is == null) throw new RuntimeException("stream is null");

            Log.d(TAG, "fileSize: " + fileSize + ",is :" + is);
            if (fileSize > 0 && is != null) {
                fos = new FileOutputStream(path);
                //把数据存入路径+文件名
                byte buf[] = new byte[1024];
                long downLoadFileSize = 0;
                do {
                    //循环读取
                    int numread = is.read(buf);
                    if (numread == -1) {
                        break;
                    }
                    fos.write(buf, 0, numread);
                    downLoadFileSize += numread;

                    mListener.onProgressChange(fileSize, downLoadFileSize);
                    //小睡10毫秒
                    Thread.sleep(5);

                } while (true);

                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "error: " + e.getMessage(), e);
        } finally {
            try {
                is.close();
            } catch (Exception ex) {
                Log.e(TAG, "error: " + ex.getMessage(), ex);
            }

            try {
                fos.close();
            } catch (Exception ex) {
                Log.e(TAG, "error: " + ex.getMessage(), ex);
            }
        }

        return false;

    }

}
