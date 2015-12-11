package com.example.pluginstudy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.pluginstudy.event.PluginMainEvent;
import com.example.pluginstudy.shared.MySharedActivity;
import com.example.pluginstudy.testwidget.WidgetActivity;

import de.greenrobot.event.EventBus;
import plugin.example.commonlib.common.PluginHelper;
import plugin.example.commonlib.common.lib.viewioc.ContentViewById;
import plugin.example.commonlib.common.lib.viewioc.ViewById;
import plugin.example.commonlib.util.MyToast;


@ContentViewById(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @ViewById(value = R.id.button1, click = "onClickView")
    Button button1;
    @ViewById(value = R.id.button2, click = "onClickView")
    Button button2;
    @ViewById(value = R.id.button3, click = "onClickView")
    Button button3;
    @ViewById(value = R.id.button4, click = "onClickView")
    Button button4;
    @ViewById(value = R.id.button5, click = "onClickView")
    Button button5;
    @ViewById(value = R.id.button6, click = "onClickView")
    Button button6;
    @ViewById(value = R.id.button7, click = "onClickView")
    Button button7;
    @ViewById(value = R.id.button8, click = "onClickView")
    Button button8;
    @ViewById(value = R.id.button9, click = "onClickView")
    Button button9;
    @ViewById(value = R.id.button10, click = "onClickView")
    Button button10;
    @ViewById(value = R.id.button11, click = "onClickView")
    Button button11;

    public static void startActivity(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");

        Intent intent = getIntent();
        String str = intent.getStringExtra(PluginHelper.EXTRA_PARAMS);

        Log.d(TAG, "Params:" + str + ",button1 is null:" + button1);
        if (!TextUtils.isEmpty(str)) {
            MyToast.show(this, str);
        }


        EventBus.getDefault().register(this);
    }

    public void onClickView(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.button1://控件测试
                jumpPage(WidgetActivity.class);
                break;
            case R.id.button2://Service测试
                jumpPage(ServiceActivity.class);
                break;
            case R.id.button3://BroadCast测试
                jumpPage(BroadCastReceiverActivity.class);
                break;
            case R.id.button4://so库测试
                jumpPage(SoActivity.class);
                break;
            case R.id.button5://数据库测试
                jumpPage(DatabaseActivity.class);
                break;
            case R.id.button6://资源测试
                jumpPage(ResourceActivity.class);
                break;
            case R.id.button7://Provider测试
                jumpPage(ProviderActivity.class);
                break;
            case R.id.button8://第三方库测试
                jumpPage(ThirdPartyActivity.class);
                break;
            case R.id.button9://SharedPreferences测试
                jumpPage(MySharedActivity.class);
                break;
        }

        MyToast.show(this, "点击是件" + id);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(plugin.example.commonlib.R.anim.right_in, plugin.example.commonlib.R.anim.left_out);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            MyToast.show(this, "onActivityResult：" + data.getStringExtra("data"));
        }
    }

    /**
     * EventBus 事件
     *
     * @param event
     */
    public void onEvent(PluginMainEvent event) {
        MyToast.show(this, "MainActivity：" + event.getParams());
    }
}
