package com.example.pluginstudy;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.hellojni.HelloJni;

import plugin.example.commonlib.common.lib.viewioc.ContentViewById;
import plugin.example.commonlib.common.lib.viewioc.ViewById;

/**
 * So库测试
 */
@ContentViewById(R.layout.activity_so)
public class SoActivity extends BaseActivity {

    @ViewById(value = R.id.button_plus, click = "onClickView")
    Button buttonPlus;

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

    public void onClickView(View v) {
        int reuslt = HelloJni.calculate(5, 8);
        Toast.makeText(getApplicationContext(), "计算结果:" + reuslt,
                Toast.LENGTH_LONG).show();

    }

}
