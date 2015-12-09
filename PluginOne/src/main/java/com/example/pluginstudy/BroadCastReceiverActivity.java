package com.example.pluginstudy;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.pluginstudy.receiver.BroadCastReceiverOne;
import com.example.pluginstudy.receiver.BroadCastReceiverThree;
import com.example.pluginstudy.receiver.BroadCastReceiverTwo;

import plugin.example.commonlib.common.PluginHelper;
import plugin.example.commonlib.common.lib.viewioc.ContentViewById;
import plugin.example.commonlib.common.lib.viewioc.ViewById;

@ContentViewById(R.layout.activity_broadcast)
public class BroadCastReceiverActivity extends BaseActivity {


    @ViewById(value = R.id.button_receiver_one, click = "onClickView")
    Button buttonReceiverOne;
    @ViewById(value = R.id.button_receiver_two, click = "onClickView")
    Button buttonReceiverTwo;
    @ViewById(value = R.id.button_receiver_three, click = "onClickView")
    Button buttonReceiverThree;

    BroadCastReceiverThree broadCastReceiverThree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IntentFilter intentFilter = new IntentFilter(BroadCastReceiverThree.ACTION_THREE);
        broadCastReceiverThree = new BroadCastReceiverThree();
        registerReceiver(broadCastReceiverThree, intentFilter);

    }

    public void onClickView(View v) {

        int id = v.getId();
        switch (id) {
            case R.id.button_receiver_one:
                startBroadCastReceive(BroadCastReceiverOne.class.getName(), "第一个");
                break;
            case R.id.button_receiver_two:
                startBroadCastReceive(BroadCastReceiverTwo.class.getName(), "第二个");
                break;
            case R.id.button_receiver_three:
                startBroadCastReceive(BroadCastReceiverThree.ACTION_THREE, "第三个");
                break;
        }

    }

    private void startBroadCastReceive(String action, String params) {
        Intent intent = new Intent(action);
        intent.putExtra(PluginHelper.EXTRA_PARAMS, params);
        sendBroadcast(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadCastReceiverThree);
    }
}
