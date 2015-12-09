package com.example.pluginstudy;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import plugin.example.commonlib.common.lib.viewioc.ContentViewById;
import plugin.example.commonlib.common.lib.viewioc.ViewById;
import plugin.example.commonlib.util.AssetsUtil;
import plugin.example.commonlib.util.MyToast;

@ContentViewById(R.layout.activity_resource)
public class ResourceActivity extends BaseActivity {

    @ViewById(value = R.id.button_assets, click = "onClickView")
    Button buttonAssets;
    @ViewById(value = R.id.button_raw, click = "onClickView")
    Button buttonRaw;
    @ViewById(value = R.id.button_common_lib, click = "onClickView")
    Button buttonCommonLib;

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
        int id = v.getId();
        switch (id) {
            case R.id.button_assets:
                try {
                    MyToast.show(this, AssetsUtil.getAssetsText(this, "DataTest.txt"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.button_raw:
                playerMusic();
                break;
            case R.id.button_common_lib:
                buttonCommonLib.setBackgroundResource(plugin.example.commonlib.R.drawable.share_weibo);
                break;
        }
    }

    public void playerMusic() {
        /** 声音播放 */
        MediaPlayer mPlayer = MediaPlayer.create(this,
                R.raw.dial_button_sound);
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
        mPlayer.start();
    }

}
