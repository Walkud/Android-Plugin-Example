package plugin.example.commonlib.util;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Walkud on 15/9/11.
 */
public class AssetsUtil {

    /**
     * 获取assets资源
     *
     * @param context
     * @param fileName 文件名称
     */
    public static String getAssetsText(Context context, String fileName) throws IOException {

        AssetManager assetManager = context.getAssets();

        InputStream in = assetManager.open(fileName);
        return readStream(in);
    }

    /**
     * 读取流
     *
     * @param in
     * @return
     */
    public static String readStream(InputStream in) throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int i;
        while ((i = in.read()) != -1) {
            baos.write(i);
        }
        return baos.toString();
    }
}
