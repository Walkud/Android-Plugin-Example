package com.example.pluginstudy;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.widget.Toast;
import android.os.Handler;

/**
 * Created by Walkud on 15/12/11.
 */
public class RootActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //初始化操作
        //比如登录操作等等
        Toast.makeText(this,"初始化...",Toast.LENGTH_SHORT).show();

        //模拟初始化耗时
        mMyHandler.sendEmptyMessageDelayed(0,1000);
    }


    private Handler mMyHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            MainActivity.startActivity(RootActivity.this);
        }
    };
}
