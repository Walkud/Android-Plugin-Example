package plugin.example.commonlib;

import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * Created by Walkud on 15/9/9.
 */
public class PluginBaseActivity extends FragmentActivity {

    /**
     * 输出当前信息 Activity TAG
     */
    protected String TAG;

    protected AppSharedPre mAppSharePre;


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        TAG = this.getClass().getSimpleName();
        mAppSharePre = AppSharedPre.getInstance(getApplicationContext());
    }


    /**
     * 注销多个广播
     *
     * @param receivers
     */
    public void unregReceiver(BroadcastReceiver... receivers) {
        for (BroadcastReceiver receiver : receivers) {
            unregisterReceiver(receiver);
        }
    }

    @Override
    public void unregisterReceiver(BroadcastReceiver receiver) {
        try {
            super.unregisterReceiver(receiver);
        } catch (Exception e) {
        }
    }
}
