package com.example.pluginstudy.database;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.pluginstudy.BaseActivity;
import com.example.pluginstudy.R;

import plugin.example.commonlib.common.lib.viewioc.ContentViewById;
import plugin.example.commonlib.common.lib.viewioc.ViewById;

@ContentViewById(R.layout.activity_db)
public class SdkDbActivity extends BaseActivity {

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

    SdkDbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new SdkDbHelper(this);
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
                db.insert("the one");
                break;
            case R.id.button3:// 更新
                db.update("the two");
                break;
            case R.id.button4:// 删除
                db.delete(null);
                queryData();
                break;
            case R.id.button5:// 查询
                queryData();
                break;
        }
    }

    private void queryData() {
        Cursor cursor = db.select();
        ListAdapter adapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                cursor,
                new String[]{SdkDbHelper.TITLE},
                new int[]{android.R.id.text1});
        list1.setAdapter(adapter);
    }

}
