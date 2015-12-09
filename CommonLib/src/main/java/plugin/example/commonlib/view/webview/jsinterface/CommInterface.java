package plugin.example.commonlib.view.webview.jsinterface;

import android.app.Activity;
import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * Created by Walkud on 15/11/27.
 * Js 公共接口
 */
public class CommInterface {

    private Context mContext;

    public CommInterface(Context context) {
        this.mContext = context;
    }

    /**
     * 显示Toast
     *
     * @param msg
     */
    @JavascriptInterface
    public void showToast(String msg, int type) {
        switch (type) {
            case 1:
                Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
                break;
            default:
                Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * 返回上一个页面
     */
    @JavascriptInterface
    public void back() {
        try {
            Activity activity = (Activity) mContext;
            activity.finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

