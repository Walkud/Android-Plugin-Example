package plugin.example.commonlib.module.main;

import android.app.Activity;
import android.os.Bundle;

/**
 * 启动页
 * Created by Walkud on 15/10/10.
 */
public class RootActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        HomeActivity.startActivity(this);
    }
}
