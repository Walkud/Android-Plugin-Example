package plugin.example.commonlib.module.main;

import android.os.Bundle;

import plugin.example.commonlib.AppContext;
import plugin.example.commonlib.common.lib.viewioc.ViewFactory;
import plugin.example.commonlib.PluginBaseActivity;

/**
 * Created by Wolkud on 15/9/9.
 */
public class BaseActivity extends PluginBaseActivity {

    protected AppContext mAppContext;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        this.mAppContext = (AppContext) getApplication();
        ViewFactory.InitActivity(this);

    }
}
