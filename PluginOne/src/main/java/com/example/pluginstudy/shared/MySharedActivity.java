package com.example.pluginstudy.shared;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.pluginstudy.BaseActivity;
import com.example.pluginstudy.R;

import plugin.example.commonlib.common.lib.viewioc.ContentViewById;
import plugin.example.commonlib.common.lib.viewioc.ViewById;
import plugin.example.commonlib.util.MyToast;

/**
 * 第三方库
 */
@ContentViewById(R.layout.activity_shared)
public class MySharedActivity extends BaseActivity {


    @ViewById(value = R.id.button_shared_1, click = "onClickView")
    Button button_shared_1;
    @ViewById(value = R.id.button_shared_11, click = "onClickView")
    Button button_shared_11;
    @ViewById(value = R.id.button_shared_2, click = "onClickView")
    Button button_shared_2;
    @ViewById(value = R.id.button_shared_3, click = "onClickView")
    Button button_shared_3;
    @ViewById(value = R.id.button_shared_4, click = "onClickView")
    Button button_shared_4;


    private SharedPreferences spre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spre = this.getSharedPreferences("app_shared", Context.MODE_APPEND);
    }

    public void onClickView(View v) {
        SharedPreferences.Editor editor = spre.edit();
        int id = v.getId();
        switch (id) {
            case R.id.button_shared_1://获取宿主程序缓存的数据
                String str = spre.getString("Test_Shared", "默认值");
                MyToast.show(this, str);
                break;
            case R.id.button_shared_11://清空宿主程序缓存的数据
                editor.remove("Test_Shared");
                editor.commit();
                break;
            case R.id.button_shared_2://缓存插件《PluginCode》文本
                editor.putString("Plugin_Data", "PluginCode");
                editor.commit();
                break;
            case R.id.button_shared_3://读取插件缓存文本
                String data = spre.getString("Plugin_Data", "默认值");
                MyToast.show(this, data);
                break;
            case R.id.button_shared_4://清空插件缓存文本
                editor.remove("Plugin_Data");
                editor.commit();
                break;

        }

    }
}