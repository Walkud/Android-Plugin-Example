package com.example.pluginstudy;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pluginstudy.provider.PluginDbTables;
import com.example.pluginstudy.provider.PluginDbTables.PluginFirstTable;

import plugin.example.commonlib.common.lib.viewioc.ContentViewById;
import plugin.example.commonlib.common.lib.viewioc.ViewById;
import plugin.example.commonlib.util.MyToast;

@ContentViewById(R.layout.activity_provider)
public class ProviderActivity extends BaseActivity {

    @ViewById(value = R.id.button2, click = "onClickView")
    Button button2;
    @ViewById(value = R.id.button3, click = "onClickView")
    Button button3;
    @ViewById(value = R.id.button4, click = "onClickView")
    Button button4;
    @ViewById(value = R.id.button5, click = "onClickView")
    Button button5;
    @ViewById(value = R.id.list1)
    ListView list1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    }

    int count = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && count == 0) {
            Toast.makeText(getApplicationContext(), "点击返回键", Toast.LENGTH_LONG)
                    .show();
            count++;
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    public void onClickView(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.button2:// 插入
                ContentValues values1 = new ContentValues();
                values1.put(PluginFirstTable.MY_FIRST_PLUGIN_NAME, getPackageName() + System.currentTimeMillis());
                getContentResolver().insert(PluginFirstTable.CONTENT_URI, values1);
                MyToast.show(this, "插入成功");
                break;
            case R.id.button3:// 更新
                ContentValues values2 = new ContentValues();
                values2.put(PluginFirstTable.MY_FIRST_PLUGIN_NAME, "@@@@@" + getPackageName() + System.currentTimeMillis());
                int c = getContentResolver().update(PluginFirstTable.CONTENT_URI, values2, null, null);
                MyToast.show(this, "已更新:" + c);
                break;
            case R.id.button4:// 清空
                int count = getContentResolver().delete(PluginFirstTable.CONTENT_URI, null, null);
                MyToast.show(this, "清空完成:" + count);
                break;
            case R.id.button5:// 查询
                Cursor cursor = getContentResolver().query(PluginDbTables.PluginFirstTable.CONTENT_URI, null, null, null, null);
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        int index = cursor.getColumnIndex(PluginDbTables.PluginFirstTable.MY_FIRST_PLUGIN_NAME);
                        if (index != -1) {
                            String pluginName = cursor.getString(index);
                            MyToast.show(this, "ContentResolver " + pluginName + " count=" + cursor.getCount());
                        }
                    } else {
                        MyToast.show(this, "无数据");
                    }
                    cursor.close();
                }
                break;
        }
    }

}
